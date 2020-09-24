package ch.dreipol.multiplatform.reduxsample.shared.redux.navigation

import ch.dreipol.dreimultiplatform.reduxkotlin.navigation.NavigationDirection
import ch.dreipol.dreimultiplatform.reduxkotlin.navigation.NavigationState
import ch.dreipol.dreimultiplatform.reduxkotlin.navigation.navigateBack
import org.reduxkotlin.Reducer

val navigationReducer: Reducer<NavigationState> = { state, action ->
    when (action) {
        NavigationAction.BACK -> navigateBack(state)
        NavigationAction.DASHBOARD, NavigationAction.ONBOARDING_END -> {
            val screens = listOf(MainScreen.DASHBOARD)
            state.copy(screens = screens, navigationDirection = NavigationDirection.PUSH)
        }
        NavigationAction.ONBOARDING_START -> {
            val screens = listOf(OnboardingScreen())
            state.copy(screens = screens, navigationDirection = NavigationDirection.PUSH)
        }
        NavigationAction.ONBOARDING_NEXT -> {
            val screens = state.screens.toMutableList()
            val lastScreen = screens.last() as OnboardingScreen
            screens.add(OnboardingScreen(lastScreen.step + 1))
            state.copy(screens = screens, navigationDirection = NavigationDirection.PUSH)
        }
        else -> state
    }
}