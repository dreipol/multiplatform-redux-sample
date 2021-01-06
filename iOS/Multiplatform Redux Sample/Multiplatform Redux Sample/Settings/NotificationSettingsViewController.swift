//
//  NotificationSettingsViewController.swift
//  Multiplatform Redux Sample
//
//  Created by Julia Strasser on 18.12.20.
//

import UIKit
import ReduxSampleShared

class NotificationSettingsViewController: PresenterViewController<NotificationSettingsView>, NotificationSettingsView {
    override var viewPresenter: Presenter<NotificationSettingsView> { NotificationSettingsViewKt.notificationSettingsPresenter }
    private let headerView = HeaderView()
    private let pushSelectionControl = PushSelectionControl(isLightTheme: true)

    override init() {
        super.init()
        view.backgroundColor = .testAppGreenLight
        vStack.addArrangedSubview(headerView)
        vStack.addSpace(kUnit3)
        vStack.addArrangedSubview(pushSelectionControl)
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    func render(notificationSettingsViewState: NotificationSettingsViewState, notificationSettings: [NotificationSettings]?) {
        headerView.titleLabel.text = notificationSettingsViewState.headerViewState.title.localized
        //TODO
//        pushSelectionControl.update(notificationSettingsViewState.re)
    }
}
