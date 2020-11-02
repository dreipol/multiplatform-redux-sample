//
//  BaseOnboardingViewController.swift
//  Multiplatform Redux Sample
//
//  Created by Julia Strasser on 30.10.20.
//

import Foundation
import UIKit
import ReduxSampleShared

class BaseOnboardingViewController: PresenterViewController<OnboardingSubView>, OnboardingSubView {
    override var viewPresenter: Presenter<OnboardingSubView> { OnboardingViewKt.onboardingSubPresenter }
    let button = PrimaryButton.autoLayout()

    func render(onboardingSubState: BaseOnboardingSubState) {
        view.backgroundColor = UIColor.testAppBlue

        button.text = "next".localized
        view.addSubview(button)
        NSLayoutConstraint.activate([
            button.bottomAnchor.constraint(equalTo: view.bottomAnchor, constant: -kUnit5),
            button.widthAnchor.constraint(equalToConstant: kButtonWidth),
            button.centerXAnchor.constraint(equalTo: view.centerXAnchor)
        ])
        print(self)
    }
}
