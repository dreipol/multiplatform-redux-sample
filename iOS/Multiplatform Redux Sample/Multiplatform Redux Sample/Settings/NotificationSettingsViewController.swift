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

    override init() {
        super.init()
        view.backgroundColor = .testAppGreenLight
        vStack.addArrangedSubview(headerView)
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    func render(notificationSettingsViewState: NotificationSettingsViewState, notificationSettings: [NotificationSettings]?) {
        headerView.titleLabel.text = notificationSettingsViewState.headerViewState.title.localized
        //TODO
    }
}
