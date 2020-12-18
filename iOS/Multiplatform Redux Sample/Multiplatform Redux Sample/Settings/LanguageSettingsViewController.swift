//
//  LanguageSettingsViewController.swift
//  Multiplatform Redux Sample
//
//  Created by Julia Strasser on 18.12.20.
//

import UIKit
import ReduxSampleShared

class LanguageSettingsViewController: PresenterViewController<LanguageSettingsView>, LanguageSettingsView {

    override var viewPresenter: Presenter<LanguageSettingsView> { LanguageSettingsViewKt.languageSettingsPresenter }
    private let headerView = HeaderView()

    override init() {
        super.init()
        view.backgroundColor = .testAppGreenLight
        vStack.addArrangedSubview(headerView)
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    func render(languageSettingsViewState: LanguageSettingsViewState, appLanguage: AppLanguage) {
        headerView.titleLabel.text = languageSettingsViewState.headerViewState.title.localized
        //TODO
    }
}
