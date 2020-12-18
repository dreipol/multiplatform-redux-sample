//
//  CalendarSettingsViewController.swift
//  Multiplatform Redux Sample
//
//  Created by Julia Strasser on 18.12.20.
//

import UIKit
import ReduxSampleShared

class CalendarSettingsViewController: PresenterViewController<CalendarSettingsView>, CalendarSettingsView {

    override var viewPresenter: Presenter<CalendarSettingsView> { CalendarSettingsViewKt.calendarSettingsPresenter }
    private let headerView = HeaderView()

    override init() {
        super.init()
        view.backgroundColor = .testAppGreenLight
        vStack.addArrangedSubview(headerView)

    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    func render(viewState: CalendarSettingsViewState) {
        headerView.titleLabel.text = viewState.headerViewState.title.localized
        //TODO
    }
}
