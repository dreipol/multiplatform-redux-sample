//
//  SettingsViewController.swift
//  Multiplatform Redux Sample
//
//  Created by Samuel Bichsel on 30.10.20.
//

import UIKit
import ReduxSampleShared

class SettingsViewController: PresenterViewController<SettingsView>, SettingsView {
    override var viewPresenter: Presenter<SettingsView> { SettingsViewKt.settingsPresenter }
    private let titleLabel = UILabel.h2()
    private let settingsTableView = UIStackView.autoLayout(axis: .vertical)
    private var allSettings: [SettingsEntry] = []

    override init() {
        super.init()
        vStack.addSpace(kUnit3)
        titleLabel.textAlignment = .left
        vStack.addArrangedSubview(titleLabel)
        vStack.addSpace(kUnit3)

        let backgroundView = UIView.autoLayout()
        backgroundView.backgroundColor = .white
        backgroundView.layer.cornerRadius = kCardCornerRadius
        settingsTableView.addSubview(backgroundView)
        backgroundView.fitSuperview()
        vStack.addArrangedSubview(settingsTableView)
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    func render(settingsViewState: SettingsViewState) {
        titleLabel.text = settingsViewState.titleKey.localized
        allSettings = settingsViewState.settings
        settingsTableView.removeAllArrangedSubviews()
        //Since we hide the licence item, there is one ite
        let lastIndex = allSettings.count - 2
        for (index, item) in allSettings.enumerated() where item.navigationAction != NavigationAction.licences {
            let control = SettingsEntryControl(model: item, isLast: index == lastIndex)
            settingsTableView.addArrangedSubview(control)
        }
    }

}

extension SettingsViewController: TabBarCompatible {
    var tabBarImageName: String { "ic_30_settings" }
}
