/*
 * Copyright (c) 2021 dreipol GmbH
 *
 * Licensed under a Creative Commons Attribution-NonCommercial 4.0 International License. A copy of the license is provided with this code.
 *
 * No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
 */

package ch.dreipol.multiplatform.reduxsample.shared.ui

import ch.dreipol.multiplatform.reduxsample.shared.utils.AppLanguage

data class LanguageSettingsViewState(
    val headerViewState: HeaderViewState = HeaderViewState("settings_language"),
    val languages: List<AppLanguage> = AppLanguage.values().toList(),
    val appLanguage: AppLanguage,
    val checkIconCDKey: String = "check_icon_contentdescription",
)

interface LanguageSettingsView : BaseView {
    override fun presenter() = languageSettingsPresenter

    fun render(languageSettingsViewState: LanguageSettingsViewState)
}

val languageSettingsPresenter = presenter<LanguageSettingsView> {
    {
        select({ it.settingsViewState.zipSettingsViewState }) {
            render(state.settingsViewState.languageSettingsViewState)
        }
    }
}