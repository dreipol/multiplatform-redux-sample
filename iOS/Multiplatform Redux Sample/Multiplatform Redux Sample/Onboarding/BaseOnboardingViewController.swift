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
    let buttonBackground = UIView.autoLayout()
    var cardIndex: Int

    init(index: Int) {
        cardIndex = index
        super.init()
        titleLabel.isHidden = true
        button.isHidden = true
        view.backgroundColor = .primaryDark

        titleLabel.textColor = .white
        titleLabel.isHidden = false
        buttonBackground.isOpaque = false
        buttonBackground.backgroundColor = UIColor.clear
        buttonBackground.addSubview(button)
        button.addTarget(self, action: #selector(primayTapped), for: .touchUpInside)
        let gradientBackgroundHeight: CGFloat = 100.0
        let gradient: CAGradientLayer = CAGradientLayer()
        gradient.backgroundColor = UIColor.clear.cgColor
        gradient.isOpaque = false
        gradient.colors = [UIColor.primaryDark.withAlphaComponent(0).cgColor, UIColor.primaryDark.withAlphaComponent(1).cgColor]
        gradient.startPoint = CGPoint(x: 1.0, y: 0.0)
        gradient.endPoint = CGPoint(x: 1.0, y: 1.0)
        gradient.frame = CGRect(x: 0.0, y: 0.0, width: self.view.frame.size.width, height: gradientBackgroundHeight)
        buttonBackground.layer.insertSublayer(gradient, at: 0)

        view.addSubview(buttonBackground)
        view.addSubview(titleLabel)
        NSLayoutConstraint.activate([
            titleLabel.topAnchor.constraint(equalTo: view.safeAreaLayoutGuide.topAnchor, constant: kUnit11),
            titleLabel.leadingAnchor.constraint(equalTo: view.leadingAnchor, constant: kUnit3),
            titleLabel.trailingAnchor.constraint(equalTo: view.trailingAnchor, constant: -kUnit3),

            buttonBackground.widthAnchor.constraint(equalTo: view.widthAnchor),
            buttonBackground.leadingAnchor.constraint(equalTo: view.leadingAnchor),
            buttonBackground.bottomAnchor.constraint(equalTo: view.safeAreaLayoutGuide.bottomAnchor),
            buttonBackground.heightAnchor.constraint(equalToConstant: gradientBackgroundHeight),

            button.bottomAnchor.constraint(equalTo: buttonBackground.bottomAnchor, constant: -kUnit3),
            button.widthAnchor.constraint(equalToConstant: kButtonWidth),
            button.centerXAnchor.constraint(equalTo: buttonBackground.centerXAnchor)
        ])

        onboardingScrollView.addSubview(vStack)
        vStack.fitVerticalScrollView()
        view.addSubview(onboardingScrollView)
        onboardingScrollView.topAnchor.constraint(equalTo: titleLabel.bottomAnchor, constant: kUnit2).isActive = true
        onboardingScrollView.leadingAnchor.constraint(equalTo: view.leadingAnchor).isActive = true
        onboardingScrollView.trailingAnchor.constraint(equalTo: view.trailingAnchor).isActive = true
        onboardingScrollView.bottomAnchor.constraint(equalTo: view.bottomAnchor).isActive = true
        onboardingScrollView.contentInset = UIEdgeInsets.init(top: 0, left: 0, bottom: gradientBackgroundHeight, right: 0)
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

}
