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
    var viewState: BaseOnboardingSubState?
    private let onboardingScrollView = UIScrollView.autoLayout()

    override init() {
        super.init()
        titleLabel.isHidden = true
        button.isHidden = true
        view.backgroundColor = UIColor.testAppBlue

        titleLabel.textColor = UIColor.testAppWhite
        titleLabel.isHidden = false
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

        onboardingScrollView.addSubview(vStack)
        vStack.fitVerticalScrollView()
        view.addSubview(onboardingScrollView)
        onboardingScrollView.topAnchor.constraint(equalTo: titleLabel.bottomAnchor, constant: kUnit2).isActive = true
        onboardingScrollView.leadingAnchor.constraint(equalTo: view.leadingAnchor).isActive = true
        onboardingScrollView.trailingAnchor.constraint(equalTo: view.trailingAnchor).isActive = true
        onboardingScrollView.bottomAnchor.constraint(equalTo: button.topAnchor).isActive = true
        vStack.alignment = .fill
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    func render(onboardingSubState: BaseOnboardingSubState) {
        viewState = onboardingSubState
        titleLabel.text = onboardingSubState.title?.localized
        button.text = onboardingSubState.primary.localized
        button.isEnabled = onboardingSubState.primaryEnabled
        button.isHidden = false
        titleLabel.isHidden = false
    }

    @objc
    func primayTapped() {
        guard let state = viewState else {
            return
        }
        _ = dispatch(state.primaryAction)
    }

    func getIndex() -> Int {
        //TODO make this nicer
        return 0
    }
}
