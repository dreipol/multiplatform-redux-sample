//
//  OnboardingFinishViewController.swift
//  Multiplatform Redux Sample
//
//  Created by Julia Strasser on 30.10.20.
//

import Foundation
import UIKit
import ReduxSampleShared

class OnboardingFinishViewController: BaseOnboardingViewController {

    let imageView = UIImageView.autoLayout()

    override func render(onboardingSubState: BaseOnboardingSubState) {
        guard onboardingSubState as? FinishState != nil else {
            return
        }
        super.render(onboardingSubState: onboardingSubState)

        imageView.image = UIImage(named: "imgOnboardingFinish")
        view.addSubview(imageView)
        NSLayoutConstraint.activate([
            imageView.centerXAnchor.constraint(equalTo: view.centerXAnchor),
            imageView.centerYAnchor.constraint(equalTo: view.centerYAnchor)
        ])
    }

    override func getIndex() -> Int {
        return 3
    }

}
