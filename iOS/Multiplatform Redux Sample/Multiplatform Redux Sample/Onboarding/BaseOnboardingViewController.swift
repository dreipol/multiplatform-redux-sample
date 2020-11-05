//
//  BaseOnboardingViewController.swift
//  Multiplatform Redux Sample
//
//  Created by Julia Strasser on 30.10.20.
//

import UIKit
import ReduxSampleShared

class BaseOnboardingViewController: PresenterViewController<OnboardingSubView>, OnboardingSubView {
    override var viewPresenter: Presenter<OnboardingSubView> { OnboardingViewKt.onboardingSubPresenter }
    let button = PrimaryButton.autoLayout()
    let titleLabel = UILabel.h2()

    func render(onboardingSubState: BaseOnboardingSubState) {
        view.backgroundColor = UIColor.testAppBlue

        titleLabel.textColor = UIColor.testAppWhite
        titleLabel.text = onboardingSubState.title?.localized
        button.text = onboardingSubState.primary.localized
        button.addTarget(self, action: #selector(primayTapped), for: .touchUpInside)
        view.addSubview(button)
        view.addSubview(titleLabel)
        NSLayoutConstraint.activate([
            titleLabel.topAnchor.constraint(equalTo: view.topAnchor, constant: kUnit11),
            titleLabel.leadingAnchor.constraint(equalTo: view.leadingAnchor, constant: kUnit3),
            titleLabel.trailingAnchor.constraint(equalTo: view.trailingAnchor, constant: -kUnit3),

            button.bottomAnchor.constraint(equalTo: view.bottomAnchor, constant: -kUnit5),
            button.widthAnchor.constraint(equalToConstant: kButtonWidth),
            button.centerXAnchor.constraint(equalTo: view.centerXAnchor)
        ])
        print(self)
    }

    @objc
    func primayTapped() {
        _ = dispatch(NavigationAction.onboardingNext)
    }

    func getIndex() -> Int {
        //TODO make this nicer
        return 0
    }
}
