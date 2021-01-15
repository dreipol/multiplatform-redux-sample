//
//  DisposalSelectionControl.swift
//  Multiplatform Redux Sample
//
//  Created by Julia Strasser on 18.12.20.
//

import UIKit
import ReduxSampleShared

class DisposalSelectionControl: UIStackView, ToggleListItemTapDelegate {

    //TODO this should be replaced by an interation over the DisposalType, but will be only availbe in Kotlin Version 1.4.30.
    //Kotlin Ticket for the issue: https://kotlinlang.slack.com/archives/C3PQML5NU/p1603904727151300
    private let allDisposals = [DisposalType.carton, DisposalType.bioWaste, DisposalType.paper, DisposalType.eTram, DisposalType.cargoTram,
                                DisposalType.textiles, DisposalType.hazardousWaste, DisposalType.sweepings]
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
        let totalCount = allDisposals.count
        for (index, disposalType) in allDisposals.enumerated() {
            let toggle = ToggleListItem(type: disposalType,
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
                    action = ThunkAction(thunk: ThunksKt.addOrRemoveNotificationThunk(disposalType: type))
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
