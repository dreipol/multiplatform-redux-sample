package ch.dreipol.multiplatform.reduxsample.shared.redux

import ch.dreipol.dreimultiplatform.reduxkotlin.navigation.NavigationDirection
import ch.dreipol.dreimultiplatform.reduxkotlin.navigation.NavigationState
import ch.dreipol.multiplatform.reduxsample.shared.database.Reminder
import ch.dreipol.multiplatform.reduxsample.shared.delight.NotificationSettings
import ch.dreipol.multiplatform.reduxsample.shared.delight.Settings
import ch.dreipol.multiplatform.reduxsample.shared.ui.*
import ch.dreipol.multiplatform.reduxsample.shared.utils.AppLanguage

private val initialNavigationState = NavigationState(listOf(MainScreen.CALENDAR), NavigationDirection.PUSH)

data class AppState(
    val settingsState: InitializableState<SettingsState> = InitializableState(SettingsState()),
    val navigationState: InitializableState<NavigationState> = InitializableState(initialNavigationState),
    val calendarViewState: CalendarViewState = CalendarViewState(),
    val infoViewState: InfoViewState = InfoViewState(),
    val settingsViewState: SettingsViewState = SettingsViewState(
        languageSettingsViewState = LanguageSettingsViewState(appLanguage = AppLanguage.fromSettingsOrDefault())
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

data class InitializableState<State>(
    private val state: State,
    val initialized: Boolean = false,
) {
    fun getState(): State? {
        return if (initialized) state else null
    }

    fun forceGetState(): State {
        return state
    }
}