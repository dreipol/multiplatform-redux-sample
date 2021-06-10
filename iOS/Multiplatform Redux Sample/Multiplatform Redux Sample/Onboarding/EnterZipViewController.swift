//
//  EnterZipViewController.swift
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

class EnterZipViewController: BaseOnboardingViewController {
    private var zipViewState: EnterZipOnboardingState?
    private let zipEnterControl = ZipEnterControl()

    init() {
        super.init(index: 0)
        vStack.addArrangedSubview(zipEnterControl)
        view.bringSubviewToFront(buttonBackground)
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    override func render(onboardingSubState: BaseOnboardingSubState) {
        guard let enterZipState = onboardingSubState as? EnterZipOnboardingState else {
            return
        }
        super.render(onboardingSubState: onboardingSubState)
        zipViewState = enterZipState
        zipEnterControl.updateControl(
            title: enterZipState.enterZipViewState.enterZipLabel.localized,
            enterText: enterZipState.enterZipViewState.selectedZip?.stringValue,
            isHiddenCollection: enterZipState.primaryEnabled,
            dataSource: enterZipState.enterZipViewState.filteredZips
        )
    }

}
