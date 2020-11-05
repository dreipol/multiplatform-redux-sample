//
//  EnterZipViewController.swift
//  Multiplatform Redux Sample
//
//  Created by Julia Strasser on 30.10.20.
//

import Foundation
import UIKit
import ReduxSampleShared

class EnterZipViewController: BaseOnboardingViewController {

    private let plzLabel = UILabel.label()

    fileprivate func addPLZLabel(_ enterZipState: EnterZipOnboardingState) {
        plzLabel.text = enterZipState.enterZipViewState.enterZipLabel.localized.uppercased()
        plzLabel.textColor = UIColor.testAppGreen
        view.addSubview(plzLabel)
        plzLabel.centerXAnchor.constraint(equalTo: view.centerXAnchor).isActive = true
        plzLabel.topAnchor.constraint(equalTo: titleLabel.bottomAnchor, constant: kUnit5).isActive = true
    }

    override func render(onboardingSubState: BaseOnboardingSubState) {
        super.render(onboardingSubState: onboardingSubState)
        guard let enterZipState = onboardingSubState as? EnterZipOnboardingState else {
            return
        }

        addPLZLabel(enterZipState)
    }

    override func getIndex() -> Int {
        return 0
    }

}
