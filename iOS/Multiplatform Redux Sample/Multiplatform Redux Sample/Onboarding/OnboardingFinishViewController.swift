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

    let imageView = UIImageView.autoLayout()

    override func render(onboardingSubState: BaseOnboardingSubState) {
        super.render(onboardingSubState: onboardingSubState)

        imageView.image = UIImage(named: "imgOnboardingFinish")
        view.addSubview(imageView)
        NSLayoutConstraint.activate([
            imageView.centerXAnchor.constraint(equalTo: view.centerXAnchor),
            imageView.centerYAnchor.constraint(equalTo: view.centerYAnchor)
        ])
    }

    @objc
    override func primayTapped() {
        _ = dispatch(NavigationAction.onboardingEnd)
    }

    override func getIndex() -> Int {
        return 3
    }

}
