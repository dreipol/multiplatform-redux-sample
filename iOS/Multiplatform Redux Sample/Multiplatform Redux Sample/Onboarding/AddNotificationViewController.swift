//
//  AddNotificationViewController.swift
//  Multiplatform Redux Sample
//
//  Created by Julia Strasser on 30.10.20.
//

import Foundation
import UIKit
import ReduxSampleShared

class AddNotificationViewController: BaseOnboardingViewController {

    private let allNotificationOpions = [RemindTime.eveningBefore, RemindTime.twoDaysBefore, RemindTime.threeDaysBefore]
    let mainPushToggle = ToggleListItem()

    private let scrollView = UIScrollView.autoLayout()
    private let vStack = UIStackView.autoLayout(axis: .vertical)
    private var allToggles = [ToggleListItem]()

    override init() {
        super.init()
        scrollView.addSubview(vStack)
        vStack.fitVerticalScrollView()
        view.addSubview(scrollView)
        scrollView.topAnchor.constraint(equalTo: titleLabel.bottomAnchor, constant: kUnit2).isActive = true
        scrollView.leadingAnchor.constraint(equalTo: view.leadingAnchor).isActive = true
        scrollView.trailingAnchor.constraint(equalTo: view.trailingAnchor).isActive = true
        scrollView.bottomAnchor.constraint(equalTo: button.topAnchor).isActive = true
        vStack.alignment = .fill

        vStack.addArrangedSubview(mainPushToggle)
        for option in allNotificationOpions {
            let toggle = ToggleListItem(notificationType: option)
            vStack.addArrangedSubview(toggle)
            allToggles.append(toggle)
        }
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    override func render(onboardingSubState: BaseOnboardingSubState) {
        guard let reminderState = onboardingSubState as? AddNotificationState else {
            return
        }
        super.render(onboardingSubState: onboardingSubState)
        mainPushToggle.setToggle(enabled: reminderState.addNotification)
        for element in reminderState.remindTimes {
            if let specificToggle = allToggles.first(where: { $0.remindType == element.first }) {
                specificToggle.setToggle(enabled: element.second?.boolValue ?? false)
                specificToggle.isEnabled = reminderState.addNotification
            }
        }
    }

    override func getIndex() -> Int {
        return 2
    }

}
