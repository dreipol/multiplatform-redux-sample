package ch.dreipol.multiplatform.reduxsample.fragments.settings

import ch.dreipol.dreimultiplatform.reduxkotlin.PresenterLifecycleObserver
import ch.dreipol.multiplatform.reduxsample.databinding.FragmentZipSettingsBinding
import ch.dreipol.multiplatform.reduxsample.fragments.BaseFragment
import ch.dreipol.multiplatform.reduxsample.shared.ui.ZipSettingsView
import ch.dreipol.multiplatform.reduxsample.shared.ui.ZipSettingsViewState

class ZipSettingsFragment : BaseFragment<FragmentZipSettingsBinding, ZipSettingsView>(), ZipSettingsView {
    override val presenterObserver = PresenterLifecycleObserver(this)

    override fun createBinding(): FragmentZipSettingsBinding {
        return FragmentZipSettingsBinding.inflate(layoutInflater)
    }

    override fun render(zipSettingsViewState: ZipSettingsViewState) {
        bindHeader(zipSettingsViewState.headerViewState, viewBinding.header)
        viewBinding.enterZipView.update(zipSettingsViewState.enterZipViewState)
    }
}