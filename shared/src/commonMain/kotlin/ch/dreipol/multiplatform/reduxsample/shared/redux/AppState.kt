package ch.dreipol.multiplatform.reduxsample.shared.redux

import ch.dreipol.dreimultiplatform.reduxkotlin.navigation.NavigationDirection
import ch.dreipol.dreimultiplatform.reduxkotlin.navigation.NavigationState
import ch.dreipol.multiplatform.reduxsample.shared.delight.NotificationSettings
import ch.dreipol.multiplatform.reduxsample.shared.delight.Settings
import ch.dreipol.multiplatform.reduxsample.shared.ui.DashboardViewState
import ch.dreipol.multiplatform.reduxsample.shared.ui.InfoViewState
import ch.dreipol.multiplatform.reduxsample.shared.ui.OnboardingViewState
import ch.dreipol.multiplatform.reduxsample.shared.ui.SettingsViewState
import ch.dreipol.multiplatform.reduxsample.shared.utils.AppLanguage
import ch.dreipol.multiplatform.reduxsample.shared.utils.MpfSettingsHelper

private val initialNavigationState = NavigationState(listOf(MainScreen.DASHBOARD), NavigationDirection.PUSH)

data class AppState(
    val settingsState: SettingsState,
    val navigationState: NavigationState = initialNavigationState,
    val dashboardViewState: DashboardViewState = DashboardViewState(),
    val infoViewState: InfoViewState = InfoViewState(),
    val settingsViewState: SettingsViewState = SettingsViewState(),
    val onboardingViewState: OnboardingViewState = OnboardingViewState()
) {

    companion object {
        fun initialState(deviceLanguage: AppLanguage): AppState {
            val appLanguage = MpfSettingsHelper.getLanguage()?.let { AppLanguage.fromValue(it) } ?: deviceLanguage
            return AppState(settingsState = SettingsState(appLanguage = appLanguage))
        }
    }
}

data class SettingsState(
    val settings: Settings? = null,
    val notificationSettings: List<NotificationSettings>? = null,
    val appLanguage: AppLanguage
)