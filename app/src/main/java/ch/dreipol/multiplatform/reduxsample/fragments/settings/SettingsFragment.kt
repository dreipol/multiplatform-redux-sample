package ch.dreipol.multiplatform.reduxsample.fragments.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ch.dreipol.dreimultiplatform.reduxkotlin.PresenterLifecycleObserver
import ch.dreipol.multiplatform.reduxsample.databinding.FragmentSettingsBinding
import ch.dreipol.multiplatform.reduxsample.fragments.BaseFragment
import ch.dreipol.multiplatform.reduxsample.shared.ui.SettingsView
import ch.dreipol.multiplatform.reduxsample.shared.ui.SettingsViewState
import ch.dreipol.multiplatform.reduxsample.utils.getString
import ch.dreipol.multiplatform.reduxsample.view.SettingsListAdapter

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
        adapter.notifyDataSetChanged()
    }
}