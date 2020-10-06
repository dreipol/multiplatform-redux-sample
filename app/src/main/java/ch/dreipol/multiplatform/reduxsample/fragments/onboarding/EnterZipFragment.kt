package ch.dreipol.multiplatform.reduxsample.fragments.onboarding

import android.os.Bundle
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import ch.dreipol.dreimultiplatform.reduxkotlin.rootDispatch
import ch.dreipol.multiplatform.reduxsample.databinding.FragmentOnboardingEnterZipBinding
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.ZipUpdatedAction
import ch.dreipol.multiplatform.reduxsample.shared.ui.BaseOnboardingSubState
import ch.dreipol.multiplatform.reduxsample.shared.ui.EnterZipState
import ch.dreipol.multiplatform.reduxsample.utils.setNewText

class EnterZipFragment : OnboardingFragment() {

    private var textWatcher: TextWatcher? = null

    private lateinit var enterZipBinding: FragmentOnboardingEnterZipBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        enterZipBinding = viewBinding.fragmentOnboardingEnterZip
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        removeTextWatcher()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        enterZipBinding.root.visibility = View.VISIBLE
    }

    override fun render(onboardingSubState: BaseOnboardingSubState) {
        super.render(onboardingSubState)
        if (onboardingSubState !is EnterZipState) return
        removeTextWatcher()
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