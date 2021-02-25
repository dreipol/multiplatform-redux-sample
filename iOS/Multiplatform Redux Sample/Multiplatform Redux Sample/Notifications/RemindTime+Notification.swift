//
//  RemindTime+Notification.swift
//  Multiplatform Redux Sample
//
//  Created by Samuel Bichsel on 25.02.21.
//

import Foundation
import ReduxSampleShared

private let reminderGeneralCategory = "REMINDER"

extension RemindTime {
    var notificationCategory: String { "\(reminderGeneralCategory)_\(name)" }
}
