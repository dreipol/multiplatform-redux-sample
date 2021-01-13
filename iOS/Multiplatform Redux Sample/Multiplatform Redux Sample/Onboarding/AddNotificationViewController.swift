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

    private let pushSelectionControl = PushSelectionControl(isLightTheme: false)

    override init() {
        super.init()
        vStack.addArrangedSubview(pushSelectionControl)
        view.bringSubviewToFront(buttonBackground)
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    override func render(onboardingSubState: BaseOnboardingSubState) {
        guard let reminderState = onboardingSubState as? AddNotificationState else {
            return
        }
        super.render(onboardingSubState: onboardingSubState)
        pushSelectionControl.update(isPushEnabled: reminderState.addNotification, remindTimes: reminderState.remindTimes)
    }

    override func getIndex() -> Int {
        return 2
    }

}
