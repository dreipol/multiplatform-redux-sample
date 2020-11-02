//
//  SelectDisposalTypesViewController.swift
//  Multiplatform Redux Sample
//
//  Created by Julia Strasser on 30.10.20.
//

import Foundation
import UIKit
import ReduxSampleShared

class SelectDisposalTypesViewController: BaseOnboardingViewController {

    override func render(onboardingSubState: BaseOnboardingSubState) {
        super.render(onboardingSubState: onboardingSubState)
        titleLabel.text = "onboarding_2_title".localized
    }

}
