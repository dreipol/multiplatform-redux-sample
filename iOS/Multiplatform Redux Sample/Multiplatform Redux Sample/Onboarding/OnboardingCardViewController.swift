//
//  OnboardingCardViewController.swift
//  Multiplatform Redux Sample
//
//  Created by Julia Strasser on 30.10.20.
//

import Foundation
import UIKit
import ReduxSampleShared


class OnboardingCardViewController: PagePresenterViewController<OnboardingView>, OnboardingView {
    override var viewPresenter: Presenter<OnboardingView> { OnboardingViewKt.onboardingPresenter }

    func render(onboardingViewState: OnboardingViewState) {
        view.backgroundColor = .green
        print("OnboardingCardViewController")

        setViewControllers([EnterZipViewController()], direction: .forward, animated: true, completion: nil)
    }

}
