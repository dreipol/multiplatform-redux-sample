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
import StoreKit

class NotificationManager: NSObject {
    let center = UNUserNotificationCenter.current()
    static let appName: String = (Bundle.main.infoDictionary?[kCFBundleNameKey as String] as? String) ?? ""
    var cancellables = Set<AnyCancellable>()

    init(store: Store) {
        super.init()
        center.delegate = self

        store.settingsStatePublisher().sink { [unowned self] state in
            if let nextReminder = state.nextReminder {
                self.schedule(nextReminder)
            } else {
                self.center.removeAllPendingNotificationRequests()
            }
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

    private func schedule(_ reminder: Reminder) {
        cancelAllNotifications()

        let remindDateComponents = reminder.remindDateComponents()

        let requests = reminder.disposals.map { disposal -> UNNotificationRequest in
            let content = UNMutableNotificationContent()
            content.title = disposal.disposalType.translationKey.localized
            content.body = Self.getTextFor(disposal: disposal)
            content.sound = UNNotificationSound.default

            let trigger = UNCalendarNotificationTrigger(dateMatching: remindDateComponents, repeats: false)

            return UNNotificationRequest(identifier: disposal.id, content: content, trigger: trigger)
        }

        for request in requests {
            center.add(request)
        }
    }

    private static func getTextFor(disposal: Disposal) -> String {
        let date = DateUtilsKt_.formatDisposalDateForNotification(disposal: disposal)
        return String(format: disposal.disposalType.notificationKey.localized, date)
    }

    private func cancelAllNotifications() {
        center.getDeliveredNotifications(completionHandler: { [center] notifications in
            if !notifications.isEmpty {
                SKStoreReviewController.requestReview()
            }

            center.removeAllDeliveredNotifications()
        })
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

        SKStoreReviewController.requestReview()
    }
}
