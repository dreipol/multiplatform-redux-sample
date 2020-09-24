package ch.dreipol.multiplatform.reduxsample.shared.redux

import ch.dreipol.multiplatform.reduxsample.shared.ui.OnboardingViewState
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class OnboardingViewStateTest {

    @Test
    fun testIsPrimaryEnabled() {
        var onboardingViewState = OnboardingViewState.create(1)
        assertFalse(onboardingViewState.primaryEnabled)
        onboardingViewState = onboardingViewState.copy(enterZipState = onboardingViewState.enterZipState.copy(selectedZip = 8000))
        assertTrue(onboardingViewState.primaryEnabled)
    }
}