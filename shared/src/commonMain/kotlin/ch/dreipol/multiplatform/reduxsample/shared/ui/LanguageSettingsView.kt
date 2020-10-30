package ch.dreipol.multiplatform.reduxsample.shared.ui

import ch.dreipol.multiplatform.reduxsample.shared.utils.AppLanguage

data class LanguageSettingsViewState(
    val headerViewState: HeaderViewState = HeaderViewState("settings_language"),
    val languages: List<AppLanguage> = AppLanguage.values().toList()
)

interface LanguageSettingsView : BaseView {
    override fun presenter() = languageSettingsPresenter

    fun render(languageSettingsViewState: LanguageSettingsViewState, appLanguage: AppLanguage)
}

val languageSettingsPresenter = presenter<LanguageSettingsView> {
    {
        val render = {
            render(state.settingsViewState.languageSettingsViewState, state.settingsState.appLanguage)
        }
        select({ it.settingsViewState.zipSettingsViewState }) {
            render()
        }
        select({ it.settingsState.appLanguage }) {
            render()
        }
    }
}