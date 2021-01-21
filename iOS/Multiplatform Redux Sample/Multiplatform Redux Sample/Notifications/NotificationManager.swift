//
//  NotificationManager.swift
//  Multiplatform Redux Sample
//
//  Created by Samuel Bichsel on 06.01.21.
//

import Combine
import Foundation
import ReduxSampleShared
import UserNotifications

class NotificationManager: NSObject {
    let center = UNUserNotificationCenter.current()
    static let appName: String = (Bundle.main.infoDictionary?[kCFBundleNameKey as String] as? String) ?? ""
    static let openAppNotificationIdentifier = "openApp"
    var cancellables = Set<AnyCancellable>()
    let store: Store

    init(store: Store) {
        self.store = store
        super.init()
        center.delegate = self

        store.settingsStatePublisher().sink { [unowned self] state in
            self.schedule(state.nextReminders)
        }.store(in: &cancellables)

        NotificationCenter.default.publisher(for: Notification.Name(rawValue: NotificationThunksKt.ShouldRequestNotificationAuthorization))
            .sink { [unowned self] _ in
                self.registerLocalNotifications()
            }.store(in: &cancellables)
    }

    private func registerLocalNotifications() {
        center.requestAuthorization(options: [.alert, .sound]) { granted, error in
            if !granted {
                DispatchQueue.main.async {
                    _ = dispatch(ThunkAction(thunk: NotificationThunksKt.removeNotificationThunk()))
                }
            }

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
            content.body = "Open the app to keep receiving reminders about upcomming collection days."
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
}

extension NotificationManager: UNUserNotificationCenterDelegate {
    func userNotificationCenter(_ center: UNUserNotificationCenter,
                                didReceive response: UNNotificationResponse,
                                withCompletionHandler completionHandler: @escaping () -> Void) {
        defer {
            completionHandler()
        }

        _ = store.dispatch(OpenedWithReminderNotification())
    }
}
