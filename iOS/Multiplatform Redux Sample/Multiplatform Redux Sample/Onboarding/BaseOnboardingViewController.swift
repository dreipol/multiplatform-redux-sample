//
//  BaseOnboardingViewController.swift
//  Multiplatform Redux Sample
//
//  Created by Julia Strasser on 30.10.20.
//

import UIKit
import ReduxSampleShared

class BaseOnboardingViewController: StackPresenterViewController<OnboardingSubView>, OnboardingSubView {
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

        view.backgroundColor = .primaryDark
        let bottomInset = UIApplication.shared.windows.first(where: { $0.isKeyWindow })?.safeAreaInsets.bottom ?? 0
        let gradientBackgroundHeight: CGFloat = 100.0 + bottomInset
        var constraints: [NSLayoutConstraint] = []
        constraints.append(contentsOf: setupButton(gradientBackgroundHeight))
        constraints.append(contentsOf: setupTitle())

        onboardingScrollView.showsVerticalScrollIndicator = false
        onboardingScrollView.contentInset = UIEdgeInsets(top: 0, left: 0, bottom: gradientBackgroundHeight, right: 0)

        onboardingScrollView.addSubview(vStack)
        vStack.alignment = .fill
        vStack.fitVerticalScrollView()
        constraints.append(vStack.trailingAnchor.constraint(equalTo: onboardingScrollView.trailingAnchor))
        view.addSubview(onboardingScrollView)
        constraints.append(onboardingScrollView.topAnchor.constraint(equalTo: titleLabel.bottomAnchor, constant: kUnit2))
        constraints.append(onboardingScrollView.leadingAnchor.constraint(equalTo: view.leadingAnchor))
        constraints.append(onboardingScrollView.trailingAnchor.constraint(equalTo: view.trailingAnchor))
        constraints.append(onboardingScrollView.bottomAnchor.constraint(equalTo: view.safeAreaLayoutGuide.bottomAnchor))

        NSLayoutConstraint.activate(constraints)
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    private func setupTitle() -> [NSLayoutConstraint] {
        titleLabel.isHidden = true
        titleLabel.textColor = .white
        titleLabel.isHidden = false
        view.addSubview(titleLabel)

        return [
            titleLabel.topAnchor.constraint(equalTo: view.safeAreaLayoutGuide.topAnchor, constant: kUnit11),
            titleLabel.leadingAnchor.constraint(equalTo: view.leadingAnchor, constant: kUnit3),
            titleLabel.trailingAnchor.constraint(equalTo: view.trailingAnchor, constant: -kUnit3)
        ]
    }

    private func setupButton(_ gradientBackgroundHeight: CGFloat) -> [NSLayoutConstraint] {
        button.isHidden = true

        buttonBackground.isOpaque = false
        buttonBackground.backgroundColor = .clear
        buttonBackground.addSubview(button)
        button.addTarget(self, action: #selector(primayTapped), for: .touchUpInside)

        let gradient = CAGradientLayer()
        gradient.backgroundColor = UIColor.clear.cgColor
        gradient.isOpaque = false
        gradient.colors = [UIColor.primaryDark.withAlphaComponent(0).cgColor, UIColor.primaryDark.withAlphaComponent(1).cgColor]
        gradient.startPoint = CGPoint(x: 1.0, y: 0.0)
        gradient.endPoint = CGPoint(x: 1.0, y: 1.0)
        gradient.frame = CGRect(x: 0.0, y: 0.0, width: self.view.frame.size.width, height: gradientBackgroundHeight)
        buttonBackground.layer.insertSublayer(gradient, at: 0)

        view.addSubview(buttonBackground)
        return [
            buttonBackground.widthAnchor.constraint(equalTo: view.widthAnchor),
            buttonBackground.leadingAnchor.constraint(equalTo: view.leadingAnchor),
            buttonBackground.bottomAnchor.constraint(equalTo: view.bottomAnchor),
            buttonBackground.heightAnchor.constraint(equalToConstant: gradientBackgroundHeight),

            button.bottomAnchor.constraint(equalTo: view.safeAreaLayoutGuide.bottomAnchor, constant: -kUnit3),
            button.widthAnchor.constraint(equalToConstant: kButtonWidth),
            button.centerXAnchor.constraint(equalTo: buttonBackground.centerXAnchor)
        ]
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
