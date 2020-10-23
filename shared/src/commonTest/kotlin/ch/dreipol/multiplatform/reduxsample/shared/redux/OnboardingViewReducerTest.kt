package ch.dreipol.multiplatform.reduxsample.shared.redux

import ch.dreipol.multiplatform.reduxsample.shared.database.RemindTime
import ch.dreipol.multiplatform.reduxsample.shared.database.SettingsDataStore
import ch.dreipol.multiplatform.reduxsample.shared.delight.Settings
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.SettingsLoadedAction
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.ZipUpdatedAction
import ch.dreipol.multiplatform.reduxsample.shared.redux.reducer.onboardingViewReducer
import ch.dreipol.multiplatform.reduxsample.shared.utils.AppLanguage
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class OnboardingViewReducerTest {

    @Test
    fun loadSettingsTest() {
        var onboardingViewState = AppState.initialState(AppLanguage.GERMAN).onboardingViewState
        assertNull(onboardingViewState.enterZipState.enterZipViewState.selectedZip)

        onboardingViewState =
            onboardingViewReducer(
                onboardingViewState,
                SettingsLoadedAction(
                    Settings(SettingsDataStore.UNDEFINED_ID, 8000, emptyList(), RemindTime.EVENING_BEFORE),
                    emptyList()
                )
            )
        assertEquals(8000, onboardingViewState.enterZipState.enterZipViewState.selectedZip)
    }

    @Test
    fun enterZipTest() {
        var onboardingViewState = AppState.initialState(AppLanguage.GERMAN).onboardingViewState
        assertNull(onboardingViewState.enterZipState.enterZipViewState.selectedZip)

        onboardingViewState = onboardingViewReducer(onboardingViewState, ZipUpdatedAction(9000))
        assertEquals(9000, onboardingViewState.enterZipState.enterZipViewState.selectedZip)

        onboardingViewState = onboardingViewReducer(onboardingViewState, ZipUpdatedAction(null))
        assertNull(onboardingViewState.enterZipState.enterZipViewState.selectedZip)
    }
}