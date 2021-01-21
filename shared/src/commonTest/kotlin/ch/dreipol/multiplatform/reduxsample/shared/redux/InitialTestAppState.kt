package ch.dreipol.multiplatform.reduxsample.shared.redux

import ch.dreipol.multiplatform.reduxsample.shared.ui.LanguageSettingsViewState
import ch.dreipol.multiplatform.reduxsample.shared.ui.SettingsViewState
import ch.dreipol.multiplatform.reduxsample.shared.utils.AppLanguage

val initialTestAppState: AppState
    get() = AppState(
        settingsViewState = SettingsViewState(languageSettingsViewState = LanguageSettingsViewState(appLanguage = AppLanguage.GERMAN))
    )