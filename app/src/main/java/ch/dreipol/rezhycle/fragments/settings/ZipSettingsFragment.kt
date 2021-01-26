package ch.dreipol.rezhycle.fragments.settings

import ch.dreipol.dreimultiplatform.reduxkotlin.PresenterLifecycleObserver
import ch.dreipol.multiplatform.reduxsample.shared.ui.ZipSettingsView
import ch.dreipol.multiplatform.reduxsample.shared.ui.ZipSettingsViewState
import ch.dreipol.rezhycle.databinding.FragmentZipSettingsBinding
import ch.dreipol.rezhycle.fragments.BaseFragment
import ch.dreipol.rezhycle.fragments.KeyboardUsingFragment

class ZipSettingsFragment : BaseFragment<FragmentZipSettingsBinding, ZipSettingsView>(), ZipSettingsView, KeyboardUsingFragment {
    override val presenterObserver = PresenterLifecycleObserver(this)

    override fun createBinding(): FragmentZipSettingsBinding {
        return FragmentZipSettingsBinding.inflate(layoutInflater)
    }

    override fun render(zipSettingsViewState: ZipSettingsViewState) {
        bindHeader(zipSettingsViewState.headerViewState, viewBinding.header)
        viewBinding.enterZipView.update(zipSettingsViewState.enterZipViewState)
    }
}