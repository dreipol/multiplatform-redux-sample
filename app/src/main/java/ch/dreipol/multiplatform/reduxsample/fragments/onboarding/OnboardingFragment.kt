package ch.dreipol.multiplatform.reduxsample.fragments.onboarding

import ch.dreipol.dreimultiplatform.reduxkotlin.PresenterLifecycleObserver
import ch.dreipol.dreimultiplatform.reduxkotlin.rootDispatch
import ch.dreipol.multiplatform.reduxsample.databinding.FragmentOnboardingBinding
import ch.dreipol.multiplatform.reduxsample.fragments.BaseFragment
import ch.dreipol.multiplatform.reduxsample.shared.redux.navigation.NavigationAction
import ch.dreipol.multiplatform.reduxsample.shared.ui.BaseOnboardingSubState
import ch.dreipol.multiplatform.reduxsample.shared.ui.OnboardingView

abstract class OnboardingFragment : BaseFragment<FragmentOnboardingBinding, OnboardingView>(), OnboardingView {
    override val presenterObserver = PresenterLifecycleObserver(this)

    override fun createBinding(): FragmentOnboardingBinding {
        return FragmentOnboardingBinding.inflate(layoutInflater)
    }

    override fun render(onboardingSubState: BaseOnboardingSubState) {
        viewBinding.title.text = onboardingSubState.title
        viewBinding.primary.text = onboardingSubState.primary
        viewBinding.primary.isEnabled = onboardingSubState.primaryEnabled
        viewBinding.primary.setOnClickListener { rootDispatch(onboardingSubState.primaryAction) }
        viewBinding.closeButton.setOnClickListener { rootDispatch(NavigationAction.ONBOARDING_END) }
        viewBinding.closeButton.isEnabled = onboardingSubState.closeEnabled
    }
}