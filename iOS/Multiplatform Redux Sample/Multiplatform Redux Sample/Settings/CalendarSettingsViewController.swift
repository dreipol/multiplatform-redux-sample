//
//  CalendarSettingsViewController.swift
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

class CalendarSettingsViewController: StackPresenterViewController<CalendarSettingsView>, CalendarSettingsView {

    override var viewPresenter: Presenter<CalendarSettingsView> { CalendarSettingsViewKt.calendarSettingsPresenter }
    private let headerView = HeaderView()
    private let introduction = UILabel.paragraph2()
    private let disposalSelectionControl = DisposalSelectionControl(isLightTheme: true, isNotification: false)

    override init() {
        super.init()
        view.backgroundColor = .primaryLight
        vStack.addArrangedSubview(headerView)
        vStack.addSpace(kUnit2)
        vStack.addArrangedSubview(introduction)
        vStack.addSpace(kUnit2)
        vStack.addArrangedSubview(disposalSelectionControl)
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    func render(viewState: CalendarSettingsViewState) {
        headerView.titleLabel.text = viewState.headerViewState.title.localized
        introduction.text = viewState.introductionKey.localized
        disposalSelectionControl.update(viewState.selectedDisposalTypes)
    }
}
