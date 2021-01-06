//
//  PushSelectionControl.swift
//  Multiplatform Redux Sample
//
//  Created by Julia Strasser on 04.01.21.
//

import UIKit
import ReduxSampleShared

class PushSelectionControl: UIStackView, ToggleListItemTapDelegate {

    private let allNotificationOpions = [RemindTime.eveningBefore, RemindTime.twoDaysBefore, RemindTime.threeDaysBefore]
    private let mainPushToggle: ToggleListItem

    private var allToggles = [ToggleListItem]()
    private let updateWithThunk: Bool

    init(isLightTheme: Bool) {
        updateWithThunk = isLightTheme
        mainPushToggle = ToggleListItem(isLast: false, isLightTheme: isLightTheme)
        super.init(frame: .zero)
        translatesAutoresizingMaskIntoConstraints = false
        axis = .vertical
        if isLightTheme {
            let backgroundView = UIView.autoLayout()
            backgroundView.backgroundColor = .white
            backgroundView.layer.cornerRadius = kCardCornerRadius
            addSubview(backgroundView)
            backgroundView.fitSuperview()
        }
        mainPushToggle.tapDelegate = self
        addArrangedSubview(mainPushToggle)
        let totalCount = allNotificationOpions.count
        for (index, option) in allNotificationOpions.enumerated() {
            let toggle = ToggleListItem(notificationType: option,
                                        isLightTheme: isLightTheme,
                                        hideBottomLine: isLightTheme && index == (totalCount-1))
            toggle.tapDelegate = self
            addArrangedSubview(toggle)
            allToggles.append(toggle)
        }
    }

    required init(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    func update(isPushEnabled: Bool, remindTimes: [KotlinPair<RemindTime, KotlinBoolean>]) {
        mainPushToggle.setToggle(enabled: isPushEnabled)
        for element in remindTimes {
            if let specificToggle = allToggles.first(where: { $0.remindType == element.first }) {
                specificToggle.setToggle(enabled: element.second?.boolValue ?? false)
                specificToggle.isEnabled = isPushEnabled
            }
        }
    }

    // MARK: ToggleListItemTapDelegate
    func didTapToggle(isOn: Bool, disposalType: DisposalType?, remindType: RemindTime?) {
        //TODO distinguish between thunk and action
        if let time = remindType {
            _ = dispatch(UpdateRemindTime(remindTime: time))
        } else {
            //Main push notification got tapped
            _ = dispatch(UpdateAddNotification(addNotification: !isOn))
        }
    }
}
