package ch.dreipol.multiplatform.reduxsample.shared.redux

import ch.dreipol.dreimultiplatform.reduxkotlin.navigation.NavigationDirection
import ch.dreipol.dreimultiplatform.reduxkotlin.navigation.NavigationState
import ch.dreipol.multiplatform.reduxsample.shared.redux.navigation.MainScreen
import ch.dreipol.multiplatform.reduxsample.shared.ui.DashboardViewState
import ch.dreipol.multiplatform.reduxsample.shared.ui.OnboardingViewState

private val initialNavigationState = NavigationState(listOf(MainScreen.DASHBOARD), NavigationDirection.PUSH)
private val initialOnboardingState = OnboardingViewState.create(1)

data class AppState(
    val navigationState: NavigationState = initialNavigationState,
    val dashboardViewState: DashboardViewState = DashboardViewState(),
    val onboardingViewState: OnboardingViewState = initialOnboardingState
) {

    companion object {
        val INITIAL_STATE = AppState()
    }
}