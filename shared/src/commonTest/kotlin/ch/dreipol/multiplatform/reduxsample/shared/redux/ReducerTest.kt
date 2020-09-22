package ch.dreipol.multiplatform.reduxsample.shared.redux

import ch.dreipol.multiplatform.reduxsample.shared.redux.navigation.NavigationAction
import ch.dreipol.multiplatform.reduxsample.shared.redux.navigation.OnboardingNavigation
import ch.dreipol.multiplatform.reduxsample.shared.redux.navigation.OnboardingScreen
import kotlin.test.Test
import kotlin.test.assertEquals

class ReducerTest {

    @Test
    fun testOnboardingNavigation() {
        var appState = AppState.INITIAL_STATE
        appState = rootReducer(appState, OnboardingNavigation())
        assertEquals(1, appState.navigationState.screens.size)
        var lastScreen = appState.navigationState.screens.last() as OnboardingScreen
        assertEquals(1, lastScreen.step)
        assertEquals(1, appState.onboardingViewState.step)

        appState = rootReducer(appState, OnboardingNavigation(2))
        assertEquals(2, appState.navigationState.screens.size)
        lastScreen = appState.navigationState.screens.last() as OnboardingScreen
        assertEquals(2, lastScreen.step)
        assertEquals(2, appState.onboardingViewState.step)

        appState = rootReducer(appState, NavigationAction.BACK)
        assertEquals(1, appState.navigationState.screens.size)
        lastScreen = appState.navigationState.screens.last() as OnboardingScreen
        assertEquals(1, lastScreen.step)
        assertEquals(1, appState.onboardingViewState.step)
    }
}