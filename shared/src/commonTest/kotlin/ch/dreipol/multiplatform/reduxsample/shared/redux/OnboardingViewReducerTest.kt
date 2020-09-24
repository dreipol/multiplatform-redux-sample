package ch.dreipol.multiplatform.reduxsample.shared.redux

import ch.dreipol.multiplatform.reduxsample.shared.database.SettingsDataStore
import ch.dreipol.multiplatform.reduxsample.shared.delight.Settings
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.SettingsLoadedAction
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.ZipUpdatedAction
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class OnboardingViewReducerTest {

    @Test
    fun loadSettingsTest() {
        var onboardingViewState = AppState.INITIAL_STATE.onboardingViewState
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
        var onboardingViewState = AppState.INITIAL_STATE.onboardingViewState
        assertNull(onboardingViewState.enterZipState.selectedZip)

        onboardingViewState = onboardingViewReducer(onboardingViewState, ZipUpdatedAction(9000))
        assertEquals(9000, onboardingViewState.enterZipState.selectedZip)

        onboardingViewState = onboardingViewReducer(onboardingViewState, ZipUpdatedAction(null))
        assertNull(onboardingViewState.enterZipState.selectedZip)
    }
}