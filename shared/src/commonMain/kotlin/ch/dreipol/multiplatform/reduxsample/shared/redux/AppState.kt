package ch.dreipol.multiplatform.reduxsample.shared.redux

import ch.dreipol.dreimultiplatform.reduxkotlin.navigation.NavigationDirection
import ch.dreipol.dreimultiplatform.reduxkotlin.navigation.NavigationState
import ch.dreipol.multiplatform.reduxsample.shared.delight.NotificationSettings
import ch.dreipol.multiplatform.reduxsample.shared.delight.Settings
import ch.dreipol.multiplatform.reduxsample.shared.redux.navigation.MainScreen
import ch.dreipol.multiplatform.reduxsample.shared.ui.DashboardViewState
import ch.dreipol.multiplatform.reduxsample.shared.ui.OnboardingViewState

private val initialNavigationState = NavigationState(listOf(MainScreen.DASHBOARD), NavigationDirection.PUSH)

data class AppState(
    val settingsState: SettingsState = SettingsState(),
    val navigationState: NavigationState = initialNavigationState,
    val dashboardViewState: DashboardViewState = DashboardViewState(),
    val onboardingViewState: OnboardingViewState = OnboardingViewState()
) {

    companion object {
        val INITIAL_STATE = AppState()
    }
}

data class SettingsState(
    val settings: Settings? = null,
    val notificationSettings: List<NotificationSettings>? = null
)