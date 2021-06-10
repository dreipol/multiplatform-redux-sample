/*
 * Copyright (c) 2021 dreipol GmbH
 *
 * Licensed under a Creative Commons Attribution-NonCommercial 4.0 International License. A copy of the license is provided with this code.
 *
 * No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
 */

package ch.dreipol.multiplatform.reduxsample.shared.redux

import ch.dreipol.dreimultiplatform.reduxkotlin.permissions.NotificationPermission
import ch.dreipol.multiplatform.reduxsample.shared.database.RemindTime
import ch.dreipol.multiplatform.reduxsample.shared.database.SettingsDataStore
import ch.dreipol.multiplatform.reduxsample.shared.delight.Settings
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.NavigationAction
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.SettingsLoadedAction
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.ZipUpdatedAction
import ch.dreipol.multiplatform.reduxsample.shared.redux.reducer.onboardingViewReducer
import kotlin.test.*

class OnboardingViewReducerTest {

    @Test
    fun loadSettingsTest() {
        var onboardingViewState = initialTestAppState.onboardingViewState
        assertNull(onboardingViewState.enterZipState.enterZipViewState.selectedZip)

        onboardingViewState =
            onboardingViewReducer(
                onboardingViewState,
                SettingsLoadedAction(
                    Settings(SettingsDataStore.UNDEFINED_ID, 8000, emptyList(), RemindTime.EVENING_BEFORE),
                    emptyList(),
                    NotificationPermission.NOT_DETERMINED
                )
            )
        assertEquals(8000, onboardingViewState.enterZipState.enterZipViewState.selectedZip)
    }

    @Test
    fun enterZipTest() {
        var onboardingViewState = initialTestAppState.onboardingViewState
        assertNull(onboardingViewState.enterZipState.enterZipViewState.selectedZip)

        onboardingViewState = onboardingViewReducer(onboardingViewState, ZipUpdatedAction(9000))
        assertEquals(9000, onboardingViewState.enterZipState.enterZipViewState.selectedZip)

        onboardingViewState = onboardingViewReducer(onboardingViewState, ZipUpdatedAction(null))
        assertNull(onboardingViewState.enterZipState.enterZipViewState.selectedZip)
    }

    @Test
    fun onboardingNextTest() {
        var onboardingViewState = initialTestAppState.onboardingViewState
        assertEquals(1, onboardingViewState.currentStep)
        assertFalse(onboardingViewState.canGoBack)

        onboardingViewState = onboardingViewReducer(onboardingViewState, NavigationAction.ONBOARDING_NEXT)
        assertEquals(2, onboardingViewState.currentStep)
        assertTrue(onboardingViewState.canGoBack)

        onboardingViewState = onboardingViewReducer(onboardingViewState, NavigationAction.ONBOARDING_NEXT)
        assertEquals(3, onboardingViewState.currentStep)
        assertTrue(onboardingViewState.canGoBack)

        onboardingViewState = onboardingViewReducer(onboardingViewState, NavigationAction.ONBOARDING_NEXT)
        assertEquals(4, onboardingViewState.currentStep)
        assertTrue(onboardingViewState.canGoBack)
    }

    @Test
    fun onboardingBackTest() {
        var onboardingViewState = initialTestAppState.onboardingViewState.copy(currentStep = 2)

        onboardingViewState = onboardingViewReducer(onboardingViewState, NavigationAction.BACK)
        assertEquals(1, onboardingViewState.currentStep)

        onboardingViewState = onboardingViewReducer(onboardingViewState, NavigationAction.BACK)
        assertEquals(1, onboardingViewState.currentStep)
    }

    @Test
    fun onboardingStartTest() {
        var onboardingViewState = initialTestAppState.onboardingViewState.copy(currentStep = 4)

        onboardingViewState = onboardingViewReducer(onboardingViewState, NavigationAction.ONBOARDING_START)
        assertEquals(1, onboardingViewState.currentStep)
    }
}