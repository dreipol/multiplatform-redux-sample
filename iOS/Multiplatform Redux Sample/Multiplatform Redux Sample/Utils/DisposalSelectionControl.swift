//
//  DisposalSelectionControl.swift
//  Multiplatform Redux Sample
//
//  Copyright (c) 2021 dreipol GmbH
//
//  Licensed under a Creative Commons Attribution-NonCommercial 4.0 International License. A copy of the license is provided with this code.
//  No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
//
//  Created by Julia Strasser on 18.12.20.
//

import UIKit
import ReduxSampleShared

class DisposalSelectionControl: UIStackView, ToggleListItemTapDelegate {
    private var allToggles = [ToggleListItem]()
    private let updateWithThunk: Bool
    private let togglesForNotifications: Bool

    init(isLightTheme: Bool, isNotification: Bool) {
        updateWithThunk = isLightTheme
        togglesForNotifications = isNotification
        super.init(frame: .zero)
        translatesAutoresizingMaskIntoConstraints = false
        axis = .vertical
        if isLightTheme {
            let backgroundView = UIView.autoLayout()
            backgroundView.backgroundColor = .white
            backgroundView.layer.cornerRadius = kCardCornerRadius
            backgroundView.layer.addShadow()
            addSubview(backgroundView)
            backgroundView.fitSuperview()
        }

        let allDisposals = Array(DisposalType.values())
        for disposalType in allDisposals {
            let toggle = ToggleListItem(type: disposalType,
                                        isLightTheme: isLightTheme,
                                        hideBottomLine: isLightTheme && disposalType == allDisposals.last)
            toggle.tapDelegate = self
            addArrangedSubview(toggle)
            allToggles.append(toggle)
        }
    }

    required init(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    func update(_ selectedDiposals: [DisposalType]) {
        for toggle in allToggles {
            if selectedDiposals.contains(where: { $0.name == toggle.disposalType?.name }) {
                toggle.setToggle(enabled: true)
            } else {
                toggle.setToggle(enabled: false)
            }
        }
    }

    // MARK: ToggleListItemTapDelegate
    func didTapToggle(isOn: Bool, disposalType: DisposalType?, remindType: RemindTime?) {
        if let type = disposalType {
            let action: Action
            if updateWithThunk {
                if togglesForNotifications {
                    action = ThunkAction(thunk: NotificationThunksKt.addOrRemoveNotificationThunk(disposalType: type))
                } else {
                    action = ThunkAction(thunk: ThunksKt.updateShowDisposalType(disposalType: type, show: !isOn))
                }
            } else {
              action = UpdateShowDisposalType(disposalType: type, show: !isOn)
            }
            _ = dispatch(action)
        }
    }
}
