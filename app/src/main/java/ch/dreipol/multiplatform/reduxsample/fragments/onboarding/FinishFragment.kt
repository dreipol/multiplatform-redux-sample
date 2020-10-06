package ch.dreipol.multiplatform.reduxsample.fragments.onboarding

import android.os.Bundle
import android.view.View

class FinishFragment : OnboardingFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.fragmentOnboardingFinish.container.visibility = View.VISIBLE
    }
}