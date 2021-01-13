//
//  PushSelectionControl.swift
//  Multiplatform Redux Sample
//
//  Created by Julia Strasser on 04.01.21.
//

import UIKit
import rezhycleShared

class PushSelectionControl: UIStackView, ToggleListItemTapDelegate {

    private let allNotificationOpions = [RemindTime.eveningBefore, RemindTime.twoDaysBefore, RemindTime.threeDaysBefore]
    private let mainPushToggle: ToggleListItem

    private var allToggles = [RemindTime: ToggleListItem]()
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
            allToggles[option] = toggle
        }
    }

    required init(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    func update(isPushEnabled: Bool, remindTimes: [KotlinPair<RemindTime, KotlinBoolean>]) {
        mainPushToggle.setToggle(enabled: isPushEnabled)
        let pairs: [(RemindTime, Bool)] = remindTimes.compactMap({ kPair in
            guard let time = kPair.first else {
                return nil
            }
            return (time, kPair.second?.boolValue ?? false)
        })

        for element in pairs {
            if let specificToggle = allToggles[element.0] {
                specificToggle.setToggle(enabled: element.1)
                specificToggle.isEnabled = isPushEnabled
            }
        }
    }

    // MARK: ToggleListItemTapDelegate
    func didTapToggle(isOn: Bool, disposalType: DisposalType?, remindType: RemindTime?) {
        let action: Action
        if let time = remindType {
            if updateWithThunk {
                action = ThunkAction(thunk: ThunksKt.setRemindTimeThunk(remindTime: time))
            } else {
                action = UpdateRemindTime(remindTime: time)
            }
        } else {
            //Main push notification got tapped
            if updateWithThunk {
                action = ThunkAction(thunk: ThunksKt.addOrRemoveNotificationThunk())
            } else {
                action = UpdateAddNotification(addNotification: !isOn)
            }
        }
        _ = dispatch(action)
    }
}
