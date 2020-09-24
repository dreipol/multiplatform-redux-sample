package ch.dreipol.multiplatform.reduxsample.shared.redux

import ch.dreipol.multiplatform.reduxsample.shared.redux.navigation.MainScreen
import ch.dreipol.multiplatform.reduxsample.shared.redux.navigation.NavigationAction
import ch.dreipol.multiplatform.reduxsample.shared.redux.navigation.OnboardingScreen
import ch.dreipol.multiplatform.reduxsample.shared.redux.navigation.navigationReducer
import kotlin.test.Test
import kotlin.test.assertEquals

class NavigationReducerTest {

    @Test
    fun testOnboardingNavigation() {
        var navigationState = AppState.INITIAL_STATE.navigationState
        navigationState = navigationReducer(navigationState, NavigationAction.ONBOARDING_START)
        assertEquals(1, navigationState.screens.size)
        var lastScreen = navigationState.screens.last() as OnboardingScreen
        assertEquals(1, lastScreen.step)

        navigationState = navigationReducer(navigationState, NavigationAction.ONBOARDING_NEXT)
        assertEquals(2, navigationState.screens.size)
        lastScreen = navigationState.screens.last() as OnboardingScreen
        assertEquals(2, lastScreen.step)

        navigationState = navigationReducer(navigationState, NavigationAction.BACK)
        assertEquals(1, navigationState.screens.size)
        lastScreen = navigationState.screens.last() as OnboardingScreen
        assertEquals(1, lastScreen.step)

        navigationState = navigationReducer(navigationState, NavigationAction.ONBOARDING_END)
        assertEquals(1, navigationState.screens.size)
        assertEquals(MainScreen.DASHBOARD, navigationState.screens.last())
    }
}