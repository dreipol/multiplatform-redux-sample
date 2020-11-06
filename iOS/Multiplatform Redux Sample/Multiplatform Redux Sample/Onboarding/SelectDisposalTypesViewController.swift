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
    private let scrollView = UIScrollView.autoLayout()
    private let vStack = UIStackView.autoLayout(axis: .vertical)

    override init() {
        super.init()
        scrollView.addSubview(vStack)
        vStack.fitVerticalScrollView()
        view.addSubview(scrollView)
        scrollView.topAnchor.constraint(equalTo: titleLabel.bottomAnchor, constant: kUnit2).isActive = true
        scrollView.leadingAnchor.constraint(equalTo: view.leadingAnchor).isActive = true
        scrollView.trailingAnchor.constraint(equalTo: view.trailingAnchor).isActive = true
        scrollView.bottomAnchor.constraint(equalTo: button.topAnchor).isActive = true

        let l1 = ToggleListItem(text: "Alle", image: nil)
        let l2 = ToggleListItem(text: DisposalType.carton.translationKey, image: DisposalType.carton.iconId)
        vStack.alignment = .fill
        vStack.addArrangedSubview(l1)
        vStack.addArrangedSubview(l2)
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    override func render(onboardingSubState: BaseOnboardingSubState) {
        guard onboardingSubState as? SelectDisposalTypesState != nil else {
            return
        }
        super.render(onboardingSubState: onboardingSubState)
    }

    override func getIndex() -> Int {
        return 1
    }

}
