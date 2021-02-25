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
        let snoozeHour = SnoozeNotification(unit: .hours, duration: 1).asAction()
        let snoozeDay = SnoozeNotification(unit: .days, duration: 1).asAction()

        let reminderEveningBefore = UNNotificationCategory(identifier: RemindTime.eveningBefore.notificationCategory,
                                                           actions: [snoozeHour],
                                                           intentIdentifiers: [])
        let reminderTwoDaysBefore = UNNotificationCategory(identifier: RemindTime.twoDaysBefore.notificationCategory,
                                                           actions: [snoozeHour, snoozeDay],
                                                           intentIdentifiers: [])
        let reminderThreeDaysBefore = UNNotificationCategory(identifier: RemindTime.twoDaysBefore.notificationCategory,
                                                             actions: [snoozeHour, snoozeDay],
                                                             intentIdentifiers: [])

        let notificationCenter = UNUserNotificationCenter.current()
        notificationCenter.setNotificationCategories([reminderEveningBefore, reminderTwoDaysBefore, reminderThreeDaysBefore])
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
            .map(\.nextReminders)
            .receive(on: DispatchQueue.global(qos: .userInitiated))
            .sink { [unowned self] reminders in
                self.schedule(reminders)
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

    private func schedule(_ reminders: [Reminder]) {
        cancelAllNotifications()

        let requests = reminders.flatMap { (reminder) -> [UNNotificationRequest] in
            let remindDateComponents = reminder.remindDateComponents()

            return reminder.disposals.map { disposal -> UNNotificationRequest in
                let content = UNMutableNotificationContent()
                content.title = disposal.disposalType.translationKey.localized
                content.body = Self.getTextFor(disposal: disposal)
                content.sound = UNNotificationSound.default
                content.categoryIdentifier = reminder.remindTime.notificationCategory

//                For debugging purposes vv
//                let trigger = UNTimeIntervalNotificationTrigger(timeInterval: 5, repeats: false)
                let trigger = UNCalendarNotificationTrigger(dateMatching: remindDateComponents, repeats: false)

                return UNNotificationRequest(identifier: disposal.id, content: content, trigger: trigger)
            }
        }

        // Only 64 notifications can be scheduled at once
        // => we schedule 63 reminders and add a notification to tell the user to open the app
        for request in requests.prefix(63) {
            center.add(request)
        }
        if let lastNotificationTrigger = requests.prefix(63).last?.trigger {
            let content = UNMutableNotificationContent()
            content.title = Self.appName
            content.body = "notification_open_app".localized
            content.sound = UNNotificationSound.default

            center.add(UNNotificationRequest(identifier: Self.openAppNotificationIdentifier,
                                             content: content,
                                             trigger: lastNotificationTrigger))
        }
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
        defer {
            completionHandler()
        }
        if let snoozeNotification = response.snoozeNotification {
            _ = store.dispatch(SnoozeNotificationAction(disposalID: response.notification.request.identifier,
                                                        unit: snoozeNotification.unit.rawValue,
                                                        duration: Int32(snoozeNotification.duration)))
        } else {
            _ = store.dispatch(OpenedWithReminderNotification())
        }
    }
}
