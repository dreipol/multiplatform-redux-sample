package ch.dreipol.multiplatform.reduxsample.shared.redux

import ch.dreipol.multiplatform.reduxsample.shared.utils.AppLanguage

val initialTestAppState: AppState
    get() = AppState(settingsState = SettingsState(appLanguage = AppLanguage.GERMAN))