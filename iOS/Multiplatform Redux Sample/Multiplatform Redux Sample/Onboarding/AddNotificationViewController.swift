//
//  AddNotificationViewController.swift
//  Multiplatform Redux Sample
//
//  Created by Julia Strasser on 30.10.20.
//

import Foundation
import UIKit
import ReduxSampleShared

class AddNotificationViewController: BaseOnboardingViewController {

    override func render(onboardingSubState: BaseOnboardingSubState) {
        guard onboardingSubState as? AddNotificationState != nil else {
            return
        }
        super.render(onboardingSubState: onboardingSubState)
    }

    override func getIndex() -> Int {
        return 2
    }

}
