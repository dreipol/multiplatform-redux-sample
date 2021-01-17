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
import BackgroundTasks

class NotificationManager {
    let center = UNUserNotificationCenter.current()
    static let appName: String = (Bundle.main.infoDictionary?[kCFBundleNameKey as String] as? String) ?? ""
    var cancellables = Set<AnyCancellable>()

    init(store: Store) {
        store.settingsStatePublisher().sink { [unowned self] state in
            if let nextReminder = state.nextReminder {
                schedule(nextReminder)
            } else {
                center.removeAllPendingNotificationRequests()
            }
        }.store(in: &cancellables)

        NotificationCenter.default.publisher(for: Notification.Name(rawValue: NotificationThunksKt.ShouldRequestNotificationAuthorization))
            .sink { [unowned self] _ in
                self.registerLocalNotifications()
            }.store(in: &cancellables)
    }

    private func registerLocalNotifications() {
        center.requestAuthorization(options: [.alert, .sound]) { granted, error in
            if granted {
//                TODO: Schedule regular update notification
            } else {
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

        var remindDateComponents = reminder.remindDateComponents()

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
        center.removeAllDeliveredNotifications()
        center.removeAllPendingNotificationRequests()
    }


}

extension NotificationManager {
    static let backgroundTaskIdentifier = "ch.dreipol.rezhycle.notifications.refresh"

    func scheduleUpdateNotification() {
        guard let identifier = Bundle.main.bundleIdentifier,
              let tomorrow = Calendar.current.date(byAdding: DateComponents(day: 1), to: Date())  else {
            //        TODO: Log correctly
            print("Identifier is nil or tomorrow ain't gonna happen.")
            return
        }

        var tomorrowDateComponents = Calendar.current.dateComponents([.year, .month, .day], from: tomorrow)
        tomorrowDateComponents.hour = 9
        tomorrowDateComponents.minute = 0

        let request = BGAppRefreshTaskRequest(identifier: Self.backgroundTaskIdentifier)
        request.earliestBeginDate = Calendar.current.date(from: tomorrowDateComponents)

        do {
            try BGTaskScheduler.shared.submit(request)
        } catch {
            print("Could not schedule app refresh: \(error)")
        }
    }
}
