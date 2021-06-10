//
//  OnboardingFinishViewController.swift
//  Multiplatform Redux Sample
//
//  Copyright (c) 2021 dreipol GmbH
//
//  Licensed under a Creative Commons Attribution-NonCommercial 4.0 International License. A copy of the license is provided with this code.
//  No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
//
//  Created by Julia Strasser on 30.10.20.
//

import Foundation
import UIKit
import ReduxSampleShared
import Lottie

class OnboardingFinishViewController: BaseOnboardingViewController {

    let imageView = UIImageView.autoLayout()
    private let loaderAnimationView = AnimationView(name: "check_animation")

    init() {
        super.init(index: 3)
        view.addSubview(loaderAnimationView)
        loaderAnimationView.fillSuperview(edgeInsets: NSDirectionalEdgeInsets(top: .zero, leading: kUnit9, bottom: .zero, trailing: kUnit9))
        loaderAnimationView.centerYAnchor.constraint(equalTo: view.centerYAnchor).isActive = true
        loaderAnimationView.centerXAnchor.constraint(equalTo: view.centerXAnchor).isActive = true
        loaderAnimationView.translatesAutoresizingMaskIntoConstraints = false
        loaderAnimationView.loopMode = .playOnce
        loaderAnimationView.backgroundBehavior = .pauseAndRestore
        view.bringSubviewToFront(buttonBackground)
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    override func render(onboardingSubState: BaseOnboardingSubState) {
        guard onboardingSubState as? FinishState != nil else {
            return
        }
        super.render(onboardingSubState: onboardingSubState)
        loaderAnimationView.play()
    }

}
