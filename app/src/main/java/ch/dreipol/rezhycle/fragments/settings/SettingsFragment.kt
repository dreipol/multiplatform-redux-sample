package ch.dreipol.rezhycle.fragments.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ch.dreipol.dreimultiplatform.reduxkotlin.PresenterLifecycleObserver
import ch.dreipol.multiplatform.reduxsample.shared.ui.SettingsView
import ch.dreipol.multiplatform.reduxsample.shared.ui.SettingsViewState
import ch.dreipol.rezhycle.databinding.FragmentSettingsBinding
import ch.dreipol.rezhycle.fragments.BaseFragment
import ch.dreipol.rezhycle.utils.getString
import ch.dreipol.rezhycle.view.SettingsListAdapter

class SettingsFragment : BaseFragment<FragmentSettingsBinding, SettingsView>(), SettingsView {
    override val presenterObserver = PresenterLifecycleObserver(this)

    private lateinit var adapter: SettingsListAdapter

    override fun createBinding(): FragmentSettingsBinding {
        return FragmentSettingsBinding.inflate(layoutInflater)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        adapter = SettingsListAdapter(listOf(), requireContext())
        viewBinding.settings.adapter = adapter
        return view
    }

    override fun render(settingsViewState: SettingsViewState) {
        viewBinding.title.text = requireContext().getString(settingsViewState.titleKey)
        adapter.settings = settingsViewState.settings
        adapter.chevronRightCD = requireContext().getString(settingsViewState.chevronRightCDKey)
        adapter.notifyDataSetChanged()
    }
}