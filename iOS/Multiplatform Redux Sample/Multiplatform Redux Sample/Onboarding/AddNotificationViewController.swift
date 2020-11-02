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
        super.render(onboardingSubState: onboardingSubState)
        titleLabel.text = "onboarding_3_title".localized
    }

}
