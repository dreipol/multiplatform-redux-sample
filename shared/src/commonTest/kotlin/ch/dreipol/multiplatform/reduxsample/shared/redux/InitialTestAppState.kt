package ch.dreipol.multiplatform.reduxsample.shared.redux

import ch.dreipol.dreimultiplatform.reduxkotlin.navigation.NavigationDirection
import ch.dreipol.dreimultiplatform.reduxkotlin.navigation.NavigationState
import ch.dreipol.multiplatform.reduxsample.shared.ui.LanguageSettingsViewState
import ch.dreipol.multiplatform.reduxsample.shared.ui.SettingsViewState
import ch.dreipol.multiplatform.reduxsample.shared.utils.AppLanguage

val initialTestAppState: AppState
    get() = AppState(
        navigationState = NavigationState(listOf(MainScreen.CALENDAR), NavigationDirection.PUSH),
        settingsViewState = SettingsViewState(languageSettingsViewState = LanguageSettingsViewState(appLanguage = AppLanguage.GERMAN)),
    )