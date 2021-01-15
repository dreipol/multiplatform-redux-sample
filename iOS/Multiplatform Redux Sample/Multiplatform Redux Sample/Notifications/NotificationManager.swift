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
    var cancellable: Cancellable?
    var history = [SettingsState]()
    let center = UNUserNotificationCenter.current()

    init(store: Store) {
        cancellable = store.settingsStatePublisher().sink { state in
            if let nextReminder = state.nextReminder {
//                TODO: Add if not already set

            } else {
//                TODO: Remove if any
            }
            self.history.append(state)
//            print("###History:\n\(self.history)")
        }
    }

    func registerLocalNotifications() {
        center.requestAuthorization(options: [.alert, .sound]) { granted, error in
            if granted {

            } else {

            }
        }
    }
}
