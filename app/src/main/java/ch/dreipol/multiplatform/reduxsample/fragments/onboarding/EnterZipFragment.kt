package ch.dreipol.multiplatform.reduxsample.fragments.onboarding

import android.text.TextWatcher
import android.view.View
import androidx.core.widget.addTextChangedListener
import ch.dreipol.dreimultiplatform.reduxkotlin.rootDispatch
import ch.dreipol.multiplatform.reduxsample.databinding.FragmentOnboardingEnterZipBinding
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.ZipUpdatedAction
import ch.dreipol.multiplatform.reduxsample.shared.ui.BaseOnboardingSubState
import ch.dreipol.multiplatform.reduxsample.shared.ui.EnterZipState
import ch.dreipol.multiplatform.reduxsample.utils.setNewText

class EnterZipFragment : OnboardingFragment() {

    private var textWatcher: TextWatcher? = null

    private val enterZipBinding: FragmentOnboardingEnterZipBinding by lazy { viewBinding.fragmentOnboardingEnterZip }

    override fun onDestroyView() {
        super.onDestroyView()
        removeTextWatcher()
    }

    override fun render(onboardingSubState: BaseOnboardingSubState) {
        super.render(onboardingSubState)
        onboardingSubState as EnterZipState
        removeTextWatcher()
        enterZipBinding.container.visibility = View.VISIBLE
        enterZipBinding.zip.setNewText(onboardingSubState.selectedZip?.toString())
        enterZipBinding.zip.visibility = View.VISIBLE
        textWatcher = enterZipBinding.zip.addTextChangedListener(
            afterTextChanged = { text ->
                val zip = text?.toString()?.toIntOrNull()
                rootDispatch(ZipUpdatedAction(zip))
            }
        )
    }

    private fun removeTextWatcher() {
        textWatcher?.let {
            enterZipBinding.zip.removeTextChangedListener(textWatcher)
            textWatcher = null
        }
    }
}