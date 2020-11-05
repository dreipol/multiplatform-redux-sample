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
        guard let _ = onboardingSubState as? SelectDisposalTypesState else {
            return
        }
        super.render(onboardingSubState: onboardingSubState)
    }

    override func getIndex() -> Int {
        return 1
    }

}
