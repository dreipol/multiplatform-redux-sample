//
//  BackgroundTaskHandler.swift
//  Multiplatform Redux Sample
//
//  Copyright (c) 2021 dreipol GmbH
//
//  Licensed under a Creative Commons Attribution-NonCommercial 4.0 International License. A copy of the license is provided with this code.
//  No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
//
//  Created by Samuel Bichsel on 01.03.21.
//

import Foundation
import UIKit.UIApplication

typealias CompletionHandler = () -> Void

class BackgroundTaskCompletionWrapper {
    let completionHandler: CompletionHandler
    var backgroundTaskID: UIBackgroundTaskIdentifier = .invalid

    init(completionHandler: @escaping CompletionHandler, taskName: String?) {
        self.completionHandler = completionHandler
        backgroundTaskID = UIApplication.shared.beginBackgroundTask(withName: taskName) {
            self.endBackgroundTaskId()
        }
    }

    deinit {
        if backgroundTaskID != .invalid {
            endBackgroundTaskId()
        }
    }

    private func endBackgroundTaskId() {
        UIApplication.shared.endBackgroundTask(self.backgroundTaskID)
        backgroundTaskID = UIBackgroundTaskIdentifier.invalid
    }

    func complete() {
        completionHandler()
        endBackgroundTaskId()
    }

    func callAsFunction() {
        complete()
    }
}
