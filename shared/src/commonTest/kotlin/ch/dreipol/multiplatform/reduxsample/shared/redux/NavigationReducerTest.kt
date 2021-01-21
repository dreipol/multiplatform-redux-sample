package ch.dreipol.multiplatform.reduxsample.shared.redux

import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.NavigationAction
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.StateInitializedAction
import ch.dreipol.multiplatform.reduxsample.shared.redux.reducer.navigationReducer
import kotlin.test.Test
import kotlin.test.assertEquals

class NavigationReducerTest {

    @Test
    fun initTest() {
        var navigationState = initialTestAppState.navigationState
        assertEquals(false, navigationState.initialized)

        navigationState = navigationReducer(navigationState, StateInitializedAction.NAVIGATION)
        assertEquals(true, navigationState.initialized)
    }

    @Test
    fun testOnboardingNavigation() {
        var navigationState = initialTestAppState.navigationState

        navigationState = navigationReducer(navigationState, NavigationAction.ONBOARDING_START)
        assertEquals(1, navigationState.forceGetState().screens.size)
        var lastScreen = navigationState.forceGetState().screens.last() as OnboardingScreen
        assertEquals(1, lastScreen.step)

        navigationState = navigationReducer(navigationState, NavigationAction.ONBOARDING_NEXT)
        assertEquals(2, navigationState.forceGetState().screens.size)
        lastScreen = navigationState.forceGetState().screens.last() as OnboardingScreen
        assertEquals(2, lastScreen.step)

        navigationState = navigationReducer(navigationState, NavigationAction.BACK)
        assertEquals(1, navigationState.forceGetState().screens.size)
        lastScreen = navigationState.forceGetState().screens.last() as OnboardingScreen
        assertEquals(1, lastScreen.step)

        navigationState = navigationReducer(navigationState, NavigationAction.ONBOARDING_END)
        assertEquals(1, navigationState.forceGetState().screens.size)
        assertEquals(MainScreen.CALENDAR, navigationState.forceGetState().screens.last())
    }
}