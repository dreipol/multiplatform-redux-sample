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

    var cancellables = Set<AnyCancellable>()

//    TODO: Debug only
    var history = [SettingsState]()

    init(store: Store) {
        store.settingsStatePublisher().sink { state in
            if let nextReminder = state.nextReminder {
//                TODO: Add if not already set

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

    func registerLocalNotifications() {
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
}
