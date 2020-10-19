package ch.dreipol.multiplatform.reduxsample.shared.redux.navigation

import ch.dreipol.dreimultiplatform.reduxkotlin.navigation.NavigationDirection
import ch.dreipol.dreimultiplatform.reduxkotlin.navigation.NavigationState
import ch.dreipol.dreimultiplatform.reduxkotlin.navigation.Screen
import ch.dreipol.dreimultiplatform.reduxkotlin.navigation.navigateBack
import org.reduxkotlin.Reducer

val navigationReducer: Reducer<NavigationState> = { state, action ->
    when (action) {
        NavigationAction.BACK -> navigateBack(state)
        NavigationAction.DASHBOARD, NavigationAction.ONBOARDING_END -> {
            val screens = listOf(MainScreen.DASHBOARD)
            state.copy(screens = screens, navigationDirection = NavigationDirection.PUSH)
        }
        NavigationAction.INFO -> {
            val screens = addScreensUntilInclusive(state.screens, MainScreen.INFORMATION)
            state.copy(screens = screens, navigationDirection = NavigationDirection.PUSH)
        }
        NavigationAction.SETTINGS -> {
            val screens = addScreensUntilInclusive(state.screens, MainScreen.SETTINGS)
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

private fun addScreensUntilInclusive(screens: List<Screen>, until: Screen): List<Screen> {
    val result = mutableListOf<Screen>()
    screens.forEach {
        result.add(it)
        if (it == until) {
            return result
        }
    }
    result.add(until)
    return result
}