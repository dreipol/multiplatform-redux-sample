package ch.dreipol.multiplatform.reduxsample.shared.redux.reducer

import ch.dreipol.dreimultiplatform.reduxkotlin.navigation.NavigationDirection
import ch.dreipol.dreimultiplatform.reduxkotlin.navigation.NavigationState
import ch.dreipol.dreimultiplatform.reduxkotlin.navigation.Screen
import ch.dreipol.dreimultiplatform.reduxkotlin.navigation.navigateBack
import ch.dreipol.multiplatform.reduxsample.shared.redux.MainScreen
import ch.dreipol.multiplatform.reduxsample.shared.redux.OnboardingScreen
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.NavigationAction
import org.reduxkotlin.Reducer

val navigationReducer: Reducer<NavigationState> = { state, action ->
    when (action) {
        NavigationAction.BACK -> navigateBack(state)
        NavigationAction.CALENDAR, NavigationAction.ONBOARDING_END -> {
            val screens = listOf(MainScreen.CALENDAR)
            state.copy(screens = screens, navigationDirection = NavigationDirection.PUSH)
        }
        NavigationAction.COLLECTION_POINT_MAP -> {
            val screens = addScreensUntilInclusive(state.screens, MainScreen.COLLECTION_POINT_MAP)
            state.copy(screens = screens, navigationDirection = NavigationDirection.PUSH)
        }
        NavigationAction.SETTINGS -> {
            val screens = addScreensUntilInclusive(state.screens, MainScreen.SETTINGS)
            state.copy(screens = screens, navigationDirection = NavigationDirection.PUSH)
        }
        NavigationAction.ZIP_SETTINGS -> {
            val screens = addScreensUntilInclusive(state.screens, MainScreen.ZIP_SETTINGS)
            state.copy(screens = screens, navigationDirection = NavigationDirection.PUSH)
        }
        NavigationAction.NOTIFICATION_SETTINGS -> {
            val screens = addScreensUntilInclusive(state.screens, MainScreen.NOTIFICATION_SETTINGS)
            state.copy(screens = screens, navigationDirection = NavigationDirection.PUSH)
        }
        NavigationAction.CALENDAR_SETTINGS -> {
            val screens = addScreensUntilInclusive(state.screens, MainScreen.CALENDAR_SETTINGS)
            state.copy(screens = screens, navigationDirection = NavigationDirection.PUSH)
        }
        NavigationAction.LANGUAGE_SETTINGS -> {
            val screens = addScreensUntilInclusive(state.screens, MainScreen.LANGUAGE_SETTINGS)
            state.copy(screens = screens, navigationDirection = NavigationDirection.PUSH)
        }
        NavigationAction.LICENCES -> {
            val screens = addScreensUntilInclusive(state.screens, MainScreen.LICENCES)
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