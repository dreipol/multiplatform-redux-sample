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

    private let disposalSelectionControl = DisposalSelectionControl(isLightTheme: false, isNotification: false)

    init() {
        super.init(index: 1)
        vStack.addArrangedSubview(disposalSelectionControl)
        view.bringSubviewToFront(buttonBackground)
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    override func render(onboardingSubState: BaseOnboardingSubState) {
        guard let disposalState = onboardingSubState as? SelectDisposalTypesState else {
            return
        }
        super.render(onboardingSubState: onboardingSubState)
        disposalSelectionControl.update(disposalState.selectedDisposalTypes)
    }

}
