//
//  EnterZipViewController.swift
//  Multiplatform Redux Sample
//
//  Created by Julia Strasser on 30.10.20.
//

import Foundation
import UIKit
import ReduxSampleShared

class EnterZipViewController: PresenterViewController<OnboardingSubView>, OnboardingSubView {
    override var viewPresenter: Presenter<OnboardingSubView> { OnboardingViewKt.onboardingSubPresenter }

    func render(onboardingSubState: BaseOnboardingSubState) {
        view.backgroundColor = .red
        let label = UILabel()
        label.text = "h"
        view.addSubview(label)
        print("hallo")
    }

}
