package ch.dreipol.multiplatform.reduxsample.shared.redux

import ch.dreipol.dreimultiplatform.reduxkotlin.navigation.NavigationDirection
import ch.dreipol.dreimultiplatform.reduxkotlin.navigation.NavigationState
import ch.dreipol.multiplatform.reduxsample.shared.database.Reminder
import ch.dreipol.multiplatform.reduxsample.shared.delight.NotificationSettings
import ch.dreipol.multiplatform.reduxsample.shared.delight.Settings
import ch.dreipol.multiplatform.reduxsample.shared.ui.*
import ch.dreipol.multiplatform.reduxsample.shared.utils.AppLanguage
import ch.dreipol.multiplatform.reduxsample.shared.utils.SettingsHelper

private val initialNavigationState = {
    val firstScreen = if (SettingsHelper.showOnboarding()) OnboardingScreen(1) else MainScreen.CALENDAR
    NavigationState(listOf(firstScreen), NavigationDirection.PUSH)
}

data class AppState(
    val appLanguage: AppLanguage = AppLanguage.fromSettingsOrDefault(),
    val settingsState: NullableState<SettingsState> = NullableState(),
    val navigationState: NavigationState = initialNavigationState(),
    val calendarViewState: CalendarViewState = CalendarViewState(),
    val infoViewState: InfoViewState = InfoViewState(),
    val settingsViewState: SettingsViewState = SettingsViewState(
        languageSettingsViewState = LanguageSettingsViewState(appLanguage = appLanguage)
    ),
    val onboardingViewState: OnboardingViewState = OnboardingViewState()
) {

    companion object {
        fun initialState(): AppState {
            return AppState()
        }
    }
}

data class SettingsState(
    val settings: Settings? = null,
    val notificationSettings: List<NotificationSettings>? = null,
    val nextReminder: Reminder? = null,
)

data class NullableState<State>(val state: State? = null)