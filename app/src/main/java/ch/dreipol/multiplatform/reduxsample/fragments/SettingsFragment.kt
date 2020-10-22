package ch.dreipol.multiplatform.reduxsample.fragments

import ch.dreipol.dreimultiplatform.reduxkotlin.PresenterLifecycleObserver
import ch.dreipol.multiplatform.reduxsample.databinding.FragmentSettingsBinding
import ch.dreipol.multiplatform.reduxsample.shared.ui.SettingsView
import ch.dreipol.multiplatform.reduxsample.shared.ui.SettingsViewState

class SettingsFragment : BaseFragment<FragmentSettingsBinding, SettingsView>(), SettingsView {
    override val presenterObserver = PresenterLifecycleObserver(this)

    override fun createBinding(): FragmentSettingsBinding {
        return FragmentSettingsBinding.inflate(layoutInflater)
    }

    override fun render(settingsViewState: SettingsViewState) {
        // TODO
    }
}