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

class NotificationManager {
    let center = UNUserNotificationCenter.current()
    static let appName: String = (Bundle.main.infoDictionary?[kCFBundleNameKey as String] as? String) ?? ""
    var cancellables = Set<AnyCancellable>()

//    TODO: Debug only
    var history = [SettingsState]()

    init(store: Store) {
        store.settingsStatePublisher().sink { [unowned self] state in
            if let nextReminder = state.nextReminder {
                schedule(nextReminder)
            } else {
//                TODO: Remove if any
            }
            self.history.append(state)
//            print("###History:\n\(self.history)")
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

        let requests = reminder.disposals.map { disposal -> UNNotificationRequest in
            let content = UNMutableNotificationContent()
            content.title = Self.appName
            content.body = Self.getTextFor(disposal: disposal)
            content.sound = UNNotificationSound.default

//            TODO: set correct date and time
//            let date = disposal.date.toiOSDate()
            let nextTriggerDate = Calendar.current.date(byAdding: .day, value: -1, to: Date())
            var dateComponents = Calendar.current.dateComponents([.year, .month, .day], from: nextTriggerDate)
            dateComponents.hour = 17
            dateComponents.minute = 00

            let trigger = UNCalendarNotificationTrigger(dateMatching: dateComponents, repeats: true)

            return UNNotificationRequest(identifier: UUID().uuidString, content: content, trigger: trigger)
        }
        for request in requests {
//            center.add(request)
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
