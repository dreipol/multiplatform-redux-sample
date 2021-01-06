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
    private let introduction = UILabel.paragraph2()
    private let disposalSelectionControl = DisposalSelectionControl(isLightTheme: true)

    override init() {
        super.init()
        view.backgroundColor = .testAppGreenLight
        vStack.addArrangedSubview(headerView)
        vStack.addSpace(kUnit3)
        vStack.addArrangedSubview(pushSelectionControl)
        vStack.addSpace(kUnit2)
        vStack.addArrangedSubview(introduction)
        vStack.addSpace(kUnit2)
        vStack.addArrangedSubview(disposalSelectionControl)
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    func render(notificationSettingsViewState: NotificationSettingsViewState) {
        headerView.titleLabel.text = notificationSettingsViewState.headerViewState.title.localized
        introduction.text = notificationSettingsViewState.introductionKey.localized
        pushSelectionControl.update(isPushEnabled: notificationSettingsViewState.notificationEnabled,
                                    remindTimes: notificationSettingsViewState.remindTimes)
        //TODO
//        disposalSelectionControl.update(viewState.selectedDisposalTypes)
    }
}
