package ch.dreipol.multiplatform.reduxsample.shared.ui

import ch.dreipol.multiplatform.reduxsample.shared.utils.AppLanguage

data class LanguageSettingsViewState(
    val headerViewState: HeaderViewState = HeaderViewState("settings_language"),
    val languages: List<AppLanguage> = AppLanguage.values().toList(),
    val appLanguage: AppLanguage,
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