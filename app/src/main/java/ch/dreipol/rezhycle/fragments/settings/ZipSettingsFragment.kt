package ch.dreipol.rezhycle.fragments.settings

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import ch.dreipol.dreimultiplatform.reduxkotlin.PresenterLifecycleObserver
import ch.dreipol.multiplatform.reduxsample.shared.ui.ZipSettingsView
import ch.dreipol.multiplatform.reduxsample.shared.ui.ZipSettingsViewState
import ch.dreipol.rezhycle.R
import ch.dreipol.rezhycle.databinding.FragmentZipSettingsBinding
import ch.dreipol.rezhycle.fragments.BaseFragment
import ch.dreipol.rezhycle.fragments.KeyboardUsingFragment
import ch.dreipol.rezhycle.fragments.SystemBarColor

class ZipSettingsFragment : BaseFragment<FragmentZipSettingsBinding, ZipSettingsView>(), ZipSettingsView, KeyboardUsingFragment {
    override val presenterObserver = PresenterLifecycleObserver(this)

    override fun createBinding(): FragmentZipSettingsBinding {
        return FragmentZipSettingsBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.enterZipView.setLabelColor(R.color.primary_dark)
        viewBinding.enterZipView.focus(requireActivity())
    }

    override fun overrideSystemBarColor(): SystemBarColor {
        return SystemBarColor.LIGHT
    }

    override fun render(zipSettingsViewState: ZipSettingsViewState) {
        bindHeader(zipSettingsViewState.headerViewState, viewBinding.header)
        if (zipSettingsViewState.enterZipViewState.canGoBack) {
            viewBinding.header.iconLeft.setColorFilter(resources.getColor(R.color.accent, null), PorterDuff.Mode.SRC_IN)
            viewBinding.header.title.setTextColor(resources.getColor(R.color.primary_dark, null))
        } else {
            val disabledColor = resources.getColor(R.color.monochromes_grey_light, null)
            viewBinding.header.iconLeft.setColorFilter(disabledColor, PorterDuff.Mode.SRC_IN)
            viewBinding.header.title.setTextColor(disabledColor)
        }

        viewBinding.enterZipView.update(zipSettingsViewState.enterZipViewState)
    }
}