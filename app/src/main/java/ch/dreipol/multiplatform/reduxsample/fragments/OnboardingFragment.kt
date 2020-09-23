package ch.dreipol.multiplatform.reduxsample.fragments

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import ch.dreipol.dreimultiplatform.reduxkotlin.PresenterLifecycleObserver
import ch.dreipol.dreimultiplatform.reduxkotlin.rootDispatch
import ch.dreipol.multiplatform.reduxsample.databinding.FragmentOnboardingBinding
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.ZipEnteredAction
import ch.dreipol.multiplatform.reduxsample.shared.redux.navigation.NavigationAction
import ch.dreipol.multiplatform.reduxsample.shared.ui.OnboardingView
import ch.dreipol.multiplatform.reduxsample.shared.ui.OnboardingViewState

class OnboardingFragment : BaseFragment<FragmentOnboardingBinding, OnboardingView>(), OnboardingView {
    override val presenterObserver = PresenterLifecycleObserver(this)

    override fun createBinding(): FragmentOnboardingBinding {
        return FragmentOnboardingBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.primary.setOnClickListener { rootDispatch(NavigationAction.ONBOARDING_NEXT) }
        viewBinding.zip.addTextChangedListener(
            afterTextChanged = { text ->
                text?.toString()?.toIntOrNull()?.let { rootDispatch(ZipEnteredAction(it)) }
            }
        )
    }

    override fun render(viewState: OnboardingViewState) {
        viewBinding.title.text = viewState.title
        viewBinding.primary.text = viewState.primary
        when (viewState.step) {
            1 -> {
                val zip = viewState.onboardingZipStep.selectedZip?.toString()
                viewBinding.zip.visibility = View.VISIBLE
                if (viewBinding.zip.text?.toString() != zip) {
                    viewBinding.zip.setText(zip)
                }
                viewBinding.primary.isEnabled = zip.isNullOrEmpty().not()
            }
            else -> {
                viewBinding.zip.visibility = View.GONE
                viewBinding.primary.isEnabled = true
            }
        }
    }
}