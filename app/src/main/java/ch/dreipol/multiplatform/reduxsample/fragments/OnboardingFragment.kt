package ch.dreipol.multiplatform.reduxsample.fragments

import android.os.Bundle
import android.text.TextWatcher
import android.view.View
import androidx.core.widget.addTextChangedListener
import ch.dreipol.dreimultiplatform.reduxkotlin.PresenterLifecycleObserver
import ch.dreipol.dreimultiplatform.reduxkotlin.rootDispatch
import ch.dreipol.multiplatform.reduxsample.databinding.FragmentOnboardingBinding
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.ZipUpdatedAction
import ch.dreipol.multiplatform.reduxsample.shared.redux.navigation.NavigationAction
import ch.dreipol.multiplatform.reduxsample.shared.ui.OnboardingView
import ch.dreipol.multiplatform.reduxsample.shared.ui.OnboardingViewState
import ch.dreipol.multiplatform.reduxsample.utils.setNewText

class OnboardingFragment : BaseFragment<FragmentOnboardingBinding, OnboardingView>(), OnboardingView {
    override val presenterObserver = PresenterLifecycleObserver(this)
    var textWatcher: TextWatcher? = null

    override fun createBinding(): FragmentOnboardingBinding {
        return FragmentOnboardingBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.primary.setOnClickListener { rootDispatch(NavigationAction.ONBOARDING_NEXT) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        removeTextWatcher()
    }

    override fun render(viewState: OnboardingViewState) {
        viewBinding.title.text = viewState.title
        viewBinding.primary.text = viewState.primary
        viewBinding.primary.isEnabled = viewState.primaryEnabled
        removeTextWatcher()
        when (viewState.step) {
            1 -> {
                viewBinding.zip.setNewText(viewState.onboardingZipStep.selectedZip?.toString())
                viewBinding.zip.visibility = View.VISIBLE
            }
            else -> {
                viewBinding.zip.visibility = View.GONE
            }
        }
        textWatcher = viewBinding.zip.addTextChangedListener(
            afterTextChanged = { text ->
                val zip = text?.toString()?.toIntOrNull()
                rootDispatch(ZipUpdatedAction(zip))
            }
        )
    }

    private fun removeTextWatcher() {
        textWatcher?.let {
            viewBinding.zip.removeTextChangedListener(textWatcher)
            textWatcher = null
        }
    }
}