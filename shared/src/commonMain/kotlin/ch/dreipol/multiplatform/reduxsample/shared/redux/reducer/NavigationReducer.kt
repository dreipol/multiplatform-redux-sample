package ch.dreipol.multiplatform.reduxsample.shared.redux.reducer

import ch.dreipol.dreimultiplatform.reduxkotlin.navigation.NavigationDirection
import ch.dreipol.dreimultiplatform.reduxkotlin.navigation.NavigationState
import ch.dreipol.dreimultiplatform.reduxkotlin.navigation.Screen
import ch.dreipol.dreimultiplatform.reduxkotlin.navigation.navigateBack
import ch.dreipol.multiplatform.reduxsample.shared.redux.InitializableState
import ch.dreipol.multiplatform.reduxsample.shared.redux.MainScreen
import ch.dreipol.multiplatform.reduxsample.shared.redux.OnboardingScreen
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.NavigationAction
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.StateInitializedAction
import org.reduxkotlin.Reducer

val navigationReducer: Reducer<InitializableState<NavigationState>> = { state, action ->
    when (action) {
        StateInitializedAction.NAVIGATION -> state.copy(initialized = true)
        NavigationAction.BACK -> copyNewState(state, navigateBack(state.forceGetState()))
        NavigationAction.CALENDAR, NavigationAction.ONBOARDING_END -> {
            val screens = listOf(MainScreen.CALENDAR)
            copyNewState(state, state.forceGetState().copy(screens = screens, navigationDirection = NavigationDirection.PUSH))
        }
        NavigationAction.INFO -> {
            val screens = addScreensUntilInclusive(state.forceGetState().screens, MainScreen.INFORMATION)
            copyNewState(state, state.forceGetState().copy(screens = screens, navigationDirection = NavigationDirection.PUSH))
        }
        NavigationAction.SETTINGS -> {
            val screens = addScreensUntilInclusive(state.forceGetState().screens, MainScreen.SETTINGS)
            copyNewState(state, state.forceGetState().copy(screens = screens, navigationDirection = NavigationDirection.PUSH))
        }
        NavigationAction.ZIP_SETTINGS -> {
            val screens = addScreensUntilInclusive(state.forceGetState().screens, MainScreen.ZIP_SETTINGS)
            copyNewState(state, state.forceGetState().copy(screens = screens, navigationDirection = NavigationDirection.PUSH))
        }
        NavigationAction.NOTIFICATION_SETTINGS -> {
            val screens = addScreensUntilInclusive(state.forceGetState().screens, MainScreen.NOTIFICATION_SETTINGS)
            copyNewState(state, state.forceGetState().copy(screens = screens, navigationDirection = NavigationDirection.PUSH))
        }
        NavigationAction.CALENDAR_SETTINGS -> {
            val screens = addScreensUntilInclusive(state.forceGetState().screens, MainScreen.CALENDAR_SETTINGS)
            copyNewState(state, state.forceGetState().copy(screens = screens, navigationDirection = NavigationDirection.PUSH))
        }
        NavigationAction.LANGUAGE_SETTINGS -> {
            val screens = addScreensUntilInclusive(state.forceGetState().screens, MainScreen.LANGUAGE_SETTINGS)
            copyNewState(state, state.forceGetState().copy(screens = screens, navigationDirection = NavigationDirection.PUSH))
        }
        NavigationAction.LICENCES -> {
            val screens = addScreensUntilInclusive(state.forceGetState().screens, MainScreen.LICENCES)
            copyNewState(state, state.forceGetState().copy(screens = screens, navigationDirection = NavigationDirection.PUSH))
        }
        NavigationAction.ONBOARDING_START -> {
            val screens = listOf(OnboardingScreen())
            copyNewState(state, state.forceGetState().copy(screens = screens, navigationDirection = NavigationDirection.PUSH))
        }
        NavigationAction.ONBOARDING_NEXT -> {
            val screens = state.forceGetState().screens.toMutableList()
            val lastScreen = screens.last() as OnboardingScreen
            screens.add(OnboardingScreen(lastScreen.step + 1))
            copyNewState(state, state.forceGetState().copy(screens = screens, navigationDirection = NavigationDirection.PUSH))
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