package ch.dreipol.rezhycle.fragments.onboarding

import android.os.Bundle
import android.view.View
import ch.dreipol.dreimultiplatform.reduxkotlin.rootDispatch
import ch.dreipol.multiplatform.reduxsample.shared.ui.BaseOnboardingSubState
import ch.dreipol.multiplatform.reduxsample.shared.ui.FinishState
import ch.dreipol.rezhycle.utils.getString

class FinishFragment : OnboardingFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.fragmentOnboardingFinish.root.visibility = View.VISIBLE
    }

    override fun render(onboardingSubState: BaseOnboardingSubState) {
        if (onboardingSubState !is FinishState) return
        super.render(onboardingSubState)
        viewBinding.fragmentOnboardingFinish.finish.contentDescription = requireContext().getString(onboardingSubState.animationCDKey)
        viewBinding.fragmentOnboardingFinish.finish.setOnClickListener { rootDispatch(onboardingSubState.primaryAction) }
    }
}