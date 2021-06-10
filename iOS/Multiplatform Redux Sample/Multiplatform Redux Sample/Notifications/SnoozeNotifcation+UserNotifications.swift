//
//  SnoozeNotifcation+UserNotifications.swift
//  Multiplatform Redux Sample
//
//  Copyright (c) 2021 dreipol GmbH
//
//  Licensed under a Creative Commons Attribution-NonCommercial 4.0 International License. A copy of the license is provided with this code.
//  No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
//
//  Created by Samuel Bichsel on 25.02.21.
//
import Foundation
import ReduxSampleShared
import UserNotifications

extension SnoozeNotification {
    fileprivate static let prefix = "snooze"

    private var unitString: String { unit.name.lowercased() }

    var notificationIdentifier: String {
        return "\(Self.prefix)_\(duration)_\(unitString)"
    }

    var notificationTitle: String {
        let numerus = duration == 1 ? "singular" : "plural"
        return "notifications_snooze_title_\(unitString)_\(numerus)".localized(self.duration)
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
