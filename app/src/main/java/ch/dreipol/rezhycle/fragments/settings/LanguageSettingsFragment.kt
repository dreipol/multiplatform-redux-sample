package ch.dreipol.rezhycle.fragments.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ch.dreipol.dreimultiplatform.reduxkotlin.PresenterLifecycleObserver
import ch.dreipol.dreimultiplatform.reduxkotlin.rootDispatch
import ch.dreipol.multiplatform.reduxsample.shared.redux.thunk.setNewAppLanguageThunk
import ch.dreipol.multiplatform.reduxsample.shared.ui.LanguageSettingsView
import ch.dreipol.multiplatform.reduxsample.shared.ui.LanguageSettingsViewState
import ch.dreipol.multiplatform.reduxsample.shared.utils.AppLanguage
import ch.dreipol.rezhycle.databinding.FragmentLanguageSettingsBinding
import ch.dreipol.rezhycle.fragments.BaseFragment
import ch.dreipol.rezhycle.utils.getString
import ch.dreipol.rezhycle.utils.restartApplication
import ch.dreipol.rezhycle.view.SelectItemListAdapter

class LanguageSettingsFragment : BaseFragment<FragmentLanguageSettingsBinding, LanguageSettingsView>(), LanguageSettingsView {
    override val presenterObserver = PresenterLifecycleObserver(this)

    private lateinit var selectItemListAdapter: SelectItemListAdapter<AppLanguage>

    override fun createBinding(): FragmentLanguageSettingsBinding {
        return FragmentLanguageSettingsBinding.inflate(layoutInflater)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        selectItemListAdapter = SelectItemListAdapter(
            listOf(), "", requireContext(), { requireContext().getString(it.descriptionKey) },
            {
                rootDispatch(setNewAppLanguageThunk(it) { restartApplication(requireActivity()) })
            }
        )
        viewBinding.languages.adapter = selectItemListAdapter
        return view
    }

    override fun render(languageSettingsViewState: LanguageSettingsViewState) {
        bindHeader(languageSettingsViewState.headerViewState, viewBinding.header)
        selectItemListAdapter.items =
            languageSettingsViewState.languages.map { if (it == languageSettingsViewState.appLanguage) it to true else it to false }
        selectItemListAdapter.checkIconCD = requireContext().getString(languageSettingsViewState.checkIconCDKey)
        selectItemListAdapter.notifyDataSetChanged()
    }
}