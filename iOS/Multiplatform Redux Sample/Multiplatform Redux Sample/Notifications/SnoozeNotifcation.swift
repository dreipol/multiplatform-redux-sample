//
//  SnoozeNotifcation.swift
//  Multiplatform Redux Sample
//
//  Created by Samuel Bichsel on 25.02.21.
//
import Foundation
import UserNotifications
import ReduxSampleShared

extension SnoozeNotification {
    fileprivate static let prefix = "snooze"

    var notificationIdentifier: String {
        return "\(Self.prefix)_\(duration)_\(unit)"
    }

    var notificationTitle: String {
        let numerus = duration == 1 ? "singular" : "plural"
        return "notifications_snooze_title_\(unit)_\(numerus)".localized(self.duration)
    }

    func asAction() -> UNNotificationAction {
        return UNNotificationAction(identifier: notificationIdentifier, title: notificationTitle)
    }

    static func createFrom(notificationIdentifier: String) -> Self? {
        let parts = notificationIdentifier.split(separator: "_")
        guard parts.count == 3,
              let duration = Int32(parts[1]),
              let unit = SnoozeUnit.Companion().fromValue(value: String(parts[2])) else {
            return nil
        }
        return Self(unit: unit, duration: duration)
    }
}

extension UNNotificationResponse {
    var snoozeNotification: SnoozeNotification? { SnoozeNotification.createFrom(notificationIdentifier: actionIdentifier) }
}
