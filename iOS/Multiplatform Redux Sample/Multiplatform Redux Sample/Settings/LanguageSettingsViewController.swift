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
    private let languageSelectionControl = LanguageSelectionControl()

    override init() {
        super.init()
        view.backgroundColor = .testAppGreenLight
        vStack.addArrangedSubview(headerView)
        vStack.addSpace(kUnit3)
        vStack.addArrangedSubview(languageSelectionControl)
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    func render(languageSettingsViewState: LanguageSettingsViewState) {
        headerView.titleLabel.text = languageSettingsViewState.headerViewState.title.localized
        languageSelectionControl.update(availableLanguages: languageSettingsViewState.languages,
                                        selectedLangauge: languageSettingsViewState.appLanguage)
    }
}
