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
import ch.dreipol.multiplatform.reduxsample.shared.redux.navigation.OnboardingScreen
import ch.dreipol.multiplatform.reduxsample.shared.ui.BaseOnboardingSubState
import ch.dreipol.multiplatform.reduxsample.shared.ui.OnboardingView
import ch.dreipol.multiplatform.reduxsample.shared.ui.OnboardingViewState
import ch.dreipol.multiplatform.reduxsample.shared.utils.getAppConfiguration
import ch.dreipol.multiplatform.reduxsample.utils.setNewText

class OnboardingFragment : BaseFragment<FragmentOnboardingBinding, OnboardingView>(), OnboardingView {
    override val presenterObserver = PresenterLifecycleObserver(this)
    private var textWatcher: TextWatcher? = null

    override fun createBinding(): FragmentOnboardingBinding {
        return FragmentOnboardingBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        removeTextWatcher()
    }

    override fun render(viewState: OnboardingViewState) {
        val currentScreen = getAppConfiguration().reduxSampleApp.store.state.navigationState.screens.last() as OnboardingScreen
        val onboardingSubState: BaseOnboardingSubState = when (currentScreen.step) {
            1 -> {
                removeTextWatcher()
                viewBinding.zip.setNewText(viewState.enterZipState.selectedZip?.toString())
                viewBinding.zip.visibility = View.VISIBLE
                textWatcher = viewBinding.zip.addTextChangedListener(
                    afterTextChanged = { text ->
                        val zip = text?.toString()?.toIntOrNull()
                        rootDispatch(ZipUpdatedAction(zip))
                    }
                )
                viewState.enterZipState
            }
            2 -> {
                viewBinding.zip.visibility = View.GONE
                viewState.selectDisposalTypesState
            }
            3 -> {
                viewBinding.zip.visibility = View.GONE
                viewState.addNotificationState
            }
            4 -> {
                viewBinding.zip.visibility = View.GONE
                viewState.finishState
            }
            else -> throw IllegalArgumentException()
        }
        viewBinding.title.text = onboardingSubState.title
        viewBinding.primary.text = onboardingSubState.primary
        viewBinding.primary.isEnabled = onboardingSubState.primaryEnabled
        viewBinding.primary.setOnClickListener { rootDispatch(onboardingSubState.primaryAction) }
        viewBinding.closeButton.setOnClickListener { rootDispatch(NavigationAction.ONBOARDING_END) }
    }

    private fun removeTextWatcher() {
        textWatcher?.let {
            viewBinding.zip.removeTextChangedListener(textWatcher)
            textWatcher = null
        }
    }
}