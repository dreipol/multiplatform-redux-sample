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

    private let plzLabel = UILabel.label()
    private let enterView = UITextField.autoLayout()
    private var zipViewState: EnterZipOnboardingState?

    override func render(onboardingSubState: BaseOnboardingSubState) {
        guard let enterZipState = onboardingSubState as? EnterZipOnboardingState else {
            return
        }
        super.render(onboardingSubState: onboardingSubState)
        zipViewState = enterZipState
        addPLZLabel(enterZipState)
        addPLZInputView()
    }

    override func getIndex() -> Int {
        return 0
    }

    fileprivate func addPLZLabel(_ enterZipState: EnterZipOnboardingState) {
        plzLabel.text = enterZipState.enterZipViewState.enterZipLabel.localized.uppercased()
        plzLabel.textColor = UIColor.testAppGreen
        view.addSubview(plzLabel)
        plzLabel.centerXAnchor.constraint(equalTo: view.centerXAnchor).isActive = true
        plzLabel.topAnchor.constraint(equalTo: titleLabel.bottomAnchor, constant: kUnit5).isActive = true
    }

    fileprivate func addPLZInputView() {
        enterView.backgroundColor = .white
        enterView.layer.cornerRadius = 4
        enterView.font = UIFont.inputLabel()
        enterView.textColor = UIColor.testAppBlack
        enterView.textAlignment = .center

        view.addSubview(enterView)
        enterView.centerXAnchor.constraint(equalTo: view.centerXAnchor).isActive = true
        enterView.widthAnchor.constraint(equalToConstant: 212).isActive = true
        enterView.heightAnchor.constraint(equalToConstant: kUnit6).isActive = true
        enterView.topAnchor.constraint(equalTo: plzLabel.bottomAnchor, constant: kUnit3).isActive = true
        enterView.keyboardType = .numberPad
        enterView.addTarget(self, action: #selector(plzValueChanged), for: .editingChanged)
    }

    @objc
    func plzValueChanged() {
        if let newValue = Int(enterView.text ?? "") {
            _ = dispatch(ZipUpdatedAction(zip: KotlinInt(integerLiteral: newValue)))
        }
    }
}
