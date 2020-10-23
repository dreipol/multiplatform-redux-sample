package ch.dreipol.multiplatform.reduxsample.fragments.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ch.dreipol.dreimultiplatform.reduxkotlin.PresenterLifecycleObserver
import ch.dreipol.dreimultiplatform.reduxkotlin.rootDispatch
import ch.dreipol.multiplatform.reduxsample.databinding.FragmentLanguageSettingsBinding
import ch.dreipol.multiplatform.reduxsample.fragments.BaseFragment
import ch.dreipol.multiplatform.reduxsample.shared.redux.setNewAppLanguageThunk
import ch.dreipol.multiplatform.reduxsample.shared.ui.LanguageSettingsView
import ch.dreipol.multiplatform.reduxsample.shared.ui.LanguageSettingsViewState
import ch.dreipol.multiplatform.reduxsample.shared.utils.AppLanguage
import ch.dreipol.multiplatform.reduxsample.utils.getString
import ch.dreipol.multiplatform.reduxsample.utils.restartApplication
import ch.dreipol.multiplatform.reduxsample.view.SelectItemListAdapter

class LanguageSettingsFragment : BaseFragment<FragmentLanguageSettingsBinding, LanguageSettingsView>(), LanguageSettingsView {
    override val presenterObserver = PresenterLifecycleObserver(this)

    private lateinit var selectItemListAdapter: SelectItemListAdapter<AppLanguage>

    override fun createBinding(): FragmentLanguageSettingsBinding {
        return FragmentLanguageSettingsBinding.inflate(layoutInflater)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        selectItemListAdapter = SelectItemListAdapter(
            listOf(), requireContext(), { requireContext().getString(it.descriptionKey) },
            {
                rootDispatch(setNewAppLanguageThunk(it) { restartApplication(requireActivity()) })
            }
        )
        viewBinding.languages.adapter = selectItemListAdapter
        return view
    }

    override fun render(languageSettingsViewState: LanguageSettingsViewState, appLanguage: AppLanguage) {
        bindHeader(languageSettingsViewState.headerViewState, viewBinding.header)
        selectItemListAdapter.items = languageSettingsViewState.languages.map { if (it == appLanguage) it to true else it to false }
        selectItemListAdapter.notifyDataSetChanged()
    }
}