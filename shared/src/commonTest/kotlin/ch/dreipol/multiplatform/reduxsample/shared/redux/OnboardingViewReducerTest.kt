package ch.dreipol.multiplatform.reduxsample.shared.redux

import ch.dreipol.multiplatform.reduxsample.shared.database.SettingsDataStore
import ch.dreipol.multiplatform.reduxsample.shared.delight.Settings
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.SettingsLoadedAction
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.ZipUpdatedAction
import ch.dreipol.multiplatform.reduxsample.shared.redux.navigation.NavigationAction
import ch.dreipol.multiplatform.reduxsample.shared.redux.navigation.OnboardingNavigation
import ch.dreipol.multiplatform.reduxsample.shared.ui.EnterZipState
import ch.dreipol.multiplatform.reduxsample.shared.ui.OnboardingViewState
import ch.dreipol.multiplatform.reduxsample.shared.ui.SelectDisposalTypesState
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class OnboardingViewReducerTest {

    @Test
    fun testOnboardingNavigation() {
        var onboardingViewState = AppState.INITIAL_STATE.onboardingViewState
        onboardingViewState = onboardingViewReducer(onboardingViewState, OnboardingNavigation())
        assertEquals(1, onboardingViewState.step)

        onboardingViewState = onboardingViewReducer(onboardingViewState, OnboardingNavigation(2))
        assertEquals(2, onboardingViewState.step)

        onboardingViewState = onboardingViewReducer(onboardingViewState, NavigationAction.BACK)
        assertEquals(1, onboardingViewState.step)
    }

    @Test
    fun testOnboardingStart() {
        var onboardingViewState = AppState.INITIAL_STATE.onboardingViewState
        onboardingViewState = onboardingViewReducer(onboardingViewState, OnboardingNavigation())
        assertNull(onboardingViewState.enterZipState.selectedZip)

        onboardingViewState =
            onboardingViewReducer(
                onboardingViewState,
                SettingsLoadedAction(
                    Settings(SettingsDataStore.UNDEFINED_ID, 8000, true, true, true, true, true, true, true, true),
                    emptyList()
                )
            )
        assertEquals(8000, onboardingViewState.enterZipState.selectedZip)
        assertEquals(true, onboardingViewState.selectDisposalTypesState.showCarton)
    }

    @Test
    fun enterZipTest() {
        var onboardingViewState = OnboardingViewState(
            1, "", "", EnterZipState(emptyList(), 8000),
            SelectDisposalTypesState(true, true, true, true, true, true, true, true)
        )
        onboardingViewState = onboardingViewReducer(onboardingViewState, ZipUpdatedAction(9000))
        assertEquals(9000, onboardingViewState.enterZipState.selectedZip)

        onboardingViewState = onboardingViewReducer(onboardingViewState, ZipUpdatedAction(null))
        assertNull(onboardingViewState.enterZipState.selectedZip)
    }
}