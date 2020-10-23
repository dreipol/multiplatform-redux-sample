package ch.dreipol.multiplatform.reduxsample.fragments.onboarding

import android.os.Bundle
import android.view.View
import ch.dreipol.multiplatform.reduxsample.shared.ui.BaseOnboardingSubState
import ch.dreipol.multiplatform.reduxsample.shared.ui.EnterZipOnboardingState

class EnterZipFragment : OnboardingFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.enterZipView.visibility = View.VISIBLE
    }

    override fun render(onboardingSubState: BaseOnboardingSubState) {
        if (onboardingSubState !is EnterZipOnboardingState) return
        super.render(onboardingSubState)
        viewBinding.enterZipView.update(onboardingSubState.enterZipViewState)
    }
}