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
    //TODO this should be replaced by an interation over the DisposalType, but will be only availbe in Kotlin Version 1.4.30.
    //Kotlin Ticket for the issue: https://kotlinlang.slack.com/archives/C3PQML5NU/p1603904727151300
    private let allDisposals = [DisposalType.carton, DisposalType.bioWaste, DisposalType.paper, DisposalType.eTram, DisposalType.cargoTram,
                                DisposalType.textiles, DisposalType.hazardousWaste, DisposalType.sweepings]

    private var allToggles = [ToggleListItem]()

    override init() {
        super.init()
        for disposalType in allDisposals {
            let toggle = ToggleListItem(type: disposalType)
            vStack.addArrangedSubview(toggle)
            allToggles.append(toggle)
        }
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    override func render(onboardingSubState: BaseOnboardingSubState) {
        guard let disposalState = onboardingSubState as? SelectDisposalTypesState else {
            return
        }
        super.render(onboardingSubState: onboardingSubState)
        for toggle in allToggles {
            if disposalState.selectedDisposalTypes.contains(where: { $0.name == toggle.disposalType?.name }) {
                toggle.setToggle(enabled: true)
            } else {
                toggle.setToggle(enabled: false)
            }
        }
    }

    override func getIndex() -> Int {
        return 1
    }

}
