package ch.dreipol.multiplatform.reduxsample.shared.redux.navigation

import ch.dreipol.dreimultiplatform.reduxkotlin.navigation.NavigationDirection
import ch.dreipol.dreimultiplatform.reduxkotlin.navigation.NavigationState
import ch.dreipol.dreimultiplatform.reduxkotlin.navigation.navigateBack
import org.reduxkotlin.Reducer

val navigationReducer: Reducer<NavigationState> = { state, action ->
    when (action) {
        NavigationAction.BACK -> navigateBack(state)
        NavigationAction.DASHBOARD -> {
            val screens = listOf(MainScreen.DASHBOARD)
            state.copy(screens = screens, navigationDirection = NavigationDirection.PUSH)
        }
        is OnboardingNavigation -> {
            val screens = when (action.step) {
                1 -> listOf(OnboardingScreen())
                else -> {
                    val screens = state.screens.toMutableList()
                    screens.add(OnboardingScreen(action.step))
                    screens
                }
            }
            state.copy(screens = screens, navigationDirection = NavigationDirection.PUSH)
        }
        else -> state
    }
}