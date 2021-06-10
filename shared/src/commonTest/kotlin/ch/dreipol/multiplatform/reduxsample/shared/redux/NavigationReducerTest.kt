/*
 * Copyright (c) 2021 dreipol GmbH
 *
 * Licensed under a Creative Commons Attribution-NonCommercial 4.0 International License. A copy of the license is provided with this code.
 *
 * No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
 */

package ch.dreipol.multiplatform.reduxsample.shared.redux

import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.NavigationAction
import ch.dreipol.multiplatform.reduxsample.shared.redux.reducer.navigationReducer
import kotlin.test.Test
import kotlin.test.assertEquals

class NavigationReducerTest {

    @Test
    fun testOnboardingNavigation() {
        var navigationState = initialTestAppState.navigationState

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
        assertEquals(MainScreen.CALENDAR, navigationState.screens.last())
    }
}