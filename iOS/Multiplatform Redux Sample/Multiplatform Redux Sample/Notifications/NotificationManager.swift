//
//  NotificationManager.swift
//  Multiplatform Redux Sample
//
//  Created by Samuel Bichsel on 06.01.21.
//

import Combine
import Foundation
import ReduxSampleShared
import UIKit.UIApplication
import UserNotifications

class NotificationManager: NSObject {
    let center = UNUserNotificationCenter.current()
    static let appName: String = (Bundle.main.infoDictionary?["CFBundleDisplayName"] as? String) ?? ""
    static let openAppNotificationIdentifier = "openApp"
    var cancellables = Set<AnyCancellable>()
    var snoozeCompletionHandler: BackgroundTaskCompletionWrapper?

    let store: Store

    init(store: Store) {
        self.store = store
        super.init()
        center.delegate = self
        registerNotificationActions()
        updateScheduledNotifications()
        subscribeToNotificationCenter()
    }

    private func registerNotificationActions() {
//        let snooze5Seconds = SnoozeNotification(unit: .seconds, duration: 5).asAction()

        let snoozeHour = SnoozeNotification(unit: .hours, duration: 1).asAction()
        let snoozeDay = SnoozeNotification(unit: .days, duration: 1).asAction()
        let reminderSameDay = UNNotificationCategory(identifier: NotificationCategory.sameDay.key,
                                                     actions: [],
                                                     intentIdentifiers: [])
        let reminderEveningBefore = UNNotificationCategory(identifier: NotificationCategory.dayBefore.key,
                                                           actions: [
                                                               // snooze5Seconds,
                                                               snoozeHour,
                                                           ],
                                                           intentIdentifiers: [])
        let reminderSeveralDaysBefore = UNNotificationCategory(identifier: NotificationCategory.severalDaysBefore.key,
                                                               actions: [snoozeHour, snoozeDay],
                                                               intentIdentifiers: [])
        let notificationCenter = UNUserNotificationCenter.current()
        notificationCenter.setNotificationCategories([reminderSameDay, reminderEveningBefore, reminderSeveralDaysBefore])
    }

    private func subscribeToNotificationCenter() {
        let center = NotificationCenter.default
        center.publisher(for: Notification.Name(rawValue: NotificationThunksKt.ShouldRequestNotificationAuthorization))
            .sink { [unowned self] _ in
                self.registerLocalNotifications()
            }.store(in: &cancellables)

        center.publisher(for: UIApplication.willEnterForegroundNotification)
            .sink { [unowned self] _ in
                self.updateAuthorizationStatus()
            }.store(in: &cancellables)
    }

    private func updateScheduledNotifications() {
        store.settingsStatePublisher()
            .receive(on: DispatchQueue.global(qos: .userInitiated))
            .map(\.nextReminders)
            .handleEvents(receiveOutput: { [unowned self] reminders in
                self.schedule(reminders)
            })
            .receive(on: DispatchQueue.main)
            .sink { [weak self] _ in
                self?.callSnoozeCompletionHandler()
            }.store(in: &cancellables)

        store.settingsStatePublisher().first().sink { [weak self] _ in
            self?.updateAuthorizationStatus()
        }.store(in: &cancellables)
    }

    private func registerLocalNotifications() {
        center.requestAuthorization(options: [.alert, .sound]) { [weak self] _, error in
            self?.updateAuthorizationStatus()
            if let e = error {
                print(e.localizedDescription)
            }
        }
    }

    fileprivate func scheduleOpenAppNotification(_ lastNotificationTrigger: UNNotificationTrigger) {
        let content = UNMutableNotificationContent()
        content.title = Self.appName
        content.body = "notification_open_app".localized
        content.sound = UNNotificationSound.default

        center.add(UNNotificationRequest(identifier: Self.openAppNotificationIdentifier,
                                         content: content,
                                         trigger: lastNotificationTrigger))
    }

    private func schedule(_ reminders: [Reminder]) {
        cancelAllNotifications()

        // Only 64 notifications can be scheduled at once
        // => we schedule 63 reminders and add a notification to tell the user to open the app
        let requests = reminders.flatMap { (reminder) -> [UNNotificationRequest] in
            let remindDateComponents = reminder.remindDateComponents()

            return reminder.disposals.map { disposal -> UNNotificationRequest in
                let content = UNMutableNotificationContent()
                content.title = disposal.disposalType.translationKey.localized
                content.body = Self.getTextFor(disposal: disposal)
                content.sound = UNNotificationSound.default
                content.categoryIdentifier = reminder.notificationCategory.key

//                For debugging purposes vv
//                let trigger = UNTimeIntervalNotificationTrigger(timeInterval: 5, repeats: false)
                let trigger = UNCalendarNotificationTrigger(dateMatching: remindDateComponents, repeats: false)

                return UNNotificationRequest(identifier: disposal.id, content: content, trigger: trigger)
            }
        }
        .prefix(2)

        for request in requests {
            center.add(request)
        }

        if let lastNotificationTrigger = requests.last?.trigger {
            scheduleOpenAppNotification(lastNotificationTrigger)
        }

    }

    private func callSnoozeCompletionHandler() {
        snoozeCompletionHandler?()
        snoozeCompletionHandler = nil
    }

    private static func getTextFor(disposal: Disposal) -> String {
        let date = DateUtilsKt_.formatDisposalDateForNotification(disposal: disposal)
        return String(format: disposal.disposalType.notificationKey.localized, date)
    }

    private func cancelAllNotifications() {
        center.removeAllDeliveredNotifications()
        center.removeAllPendingNotificationRequests()
    }

    private func updateAuthorizationStatus() {
        center.getNotificationSettings { [weak store] settings in
            let status = settings.authorizationStatus
            DispatchQueue.main.async {
                let action = ThunkAction(thunk: PermissionsThunkKt.didReceiveNotificationPermissionThunk(rawValue: Int32(status.rawValue)))
                _ = store?.dispatch(action)
            }
        }
    }
}

extension NotificationManager: UNUserNotificationCenterDelegate {
    func userNotificationCenter(_ center: UNUserNotificationCenter,
                                didReceive response: UNNotificationResponse,
                                withCompletionHandler completionHandler: @escaping () -> Void) {
        if let snoozeNotification = response.snoozeNotification {
            let thunk = NotificationThunksKt.snoozeNotification(disposalId: response.notification.request.identifier,
                                                                snoozeNotification: snoozeNotification)
            snoozeCompletionHandler = BackgroundTaskCompletionWrapper(completionHandler: completionHandler,
                                                                      taskName: "Update Reminders")
            _ = store.dispatch(ThunkAction(thunk: thunk))
        } else {
            _ = store.dispatch(OpenedWithReminderNotification())
            completionHandler()
        }
    }
}
