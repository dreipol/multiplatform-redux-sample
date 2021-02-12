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
            title: enterZipState.enterZipViewState.enterZipLabel.localized.uppercased(),
            enterText: enterZipState.enterZipViewState.selectedZip?.stringValue,
            isHiddenCollection: enterZipState.primaryEnabled,
            dataSource: enterZipState.enterZipViewState.filteredZips
        )
    }

}
