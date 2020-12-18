//
//  DisposalSelectionControl.swift
//  Multiplatform Redux Sample
//
//  Created by Julia Strasser on 18.12.20.
//

import UIKit
import ReduxSampleShared

class DisposalSelectionControl: UIStackView {
    //TODO this should be replaced by an interation over the DisposalType, but will be only availbe in Kotlin Version 1.4.30.
    //Kotlin Ticket for the issue: https://kotlinlang.slack.com/archives/C3PQML5NU/p1603904727151300
    private let allDisposals = [DisposalType.carton, DisposalType.bioWaste, DisposalType.paper, DisposalType.eTram, DisposalType.cargoTram,
                                DisposalType.textiles, DisposalType.hazardousWaste, DisposalType.sweepings]
    private var allToggles = [ToggleListItem]()

    init(isLightTheme: Bool) {
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
        let totalCount = allDisposals.count
        for (index, disposalType) in allDisposals.enumerated() {
            let toggle = ToggleListItem(type: disposalType, isLightTheme: isLightTheme, isLast: isLightTheme && index == (totalCount-1))
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
}
