//
//  OnboardingFinishViewController.swift
//  Multiplatform Redux Sample
//
//  Created by Julia Strasser on 30.10.20.
//

import Foundation
import UIKit
import ReduxSampleShared
import dreiKit

class OnboardingFinishViewController: BaseOnboardingViewController {

    func addDemoButton() {
        let finishButton = UIButton.autoLayout()
        finishButton.setTitle("Finish", for: .normal)
        finishButton.addTarget(self, action: #selector(endOnboarding), for: .touchUpInside)
        view.addSubview(finishButton)

        NSLayoutConstraint.activate([
            finishButton.centerXAnchor.constraint(equalTo: view.centerXAnchor),
            finishButton.centerYAnchor.constraint(equalTo: view.centerYAnchor)
        ])
    }

    override func render(onboardingSubState: BaseOnboardingSubState) {
        super.render(onboardingSubState: onboardingSubState)
        addDemoButton()
    }

    @objc
    func endOnboarding() {
        _ = dispatch(NavigationAction.onboardingEnd)
    }
}
