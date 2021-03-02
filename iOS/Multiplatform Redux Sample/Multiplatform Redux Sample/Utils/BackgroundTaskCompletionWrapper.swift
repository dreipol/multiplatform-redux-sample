//
//  BackgroundTaskHandler.swift
//  Multiplatform Redux Sample
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
