package ch.dreipol.rezhycle.fragments.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ch.dreipol.dreimultiplatform.reduxkotlin.PresenterLifecycleObserver
import ch.dreipol.dreimultiplatform.reduxkotlin.rootDispatch
import ch.dreipol.multiplatform.reduxsample.shared.database.DisposalType
import ch.dreipol.multiplatform.reduxsample.shared.redux.thunk.updateShowDisposalType
import ch.dreipol.multiplatform.reduxsample.shared.ui.CalendarSettingsView
import ch.dreipol.multiplatform.reduxsample.shared.ui.CalendarSettingsViewState
import ch.dreipol.rezhycle.R
import ch.dreipol.rezhycle.databinding.FragmentCalendarSettingsBinding
import ch.dreipol.rezhycle.fragments.BaseFragment
import ch.dreipol.rezhycle.fragments.SystemBarColor
import ch.dreipol.rezhycle.utils.getString
import ch.dreipol.rezhycle.view.SelectDisposalTypesAdapter

class CalendarSettingsFragment : BaseFragment<FragmentCalendarSettingsBinding, CalendarSettingsView>(), CalendarSettingsView {

    private lateinit var disposalTypesAdapter: SelectDisposalTypesAdapter

    override val presenterObserver = PresenterLifecycleObserver(this)

    override fun createBinding(): FragmentCalendarSettingsBinding {
        return FragmentCalendarSettingsBinding.inflate(layoutInflater)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        disposalTypesAdapter = SelectDisposalTypesAdapter(
            requireContext(), emptyMap(), R.color.primary_dark,
            { isChecked, disposalType ->
                rootDispatch(updateShowDisposalType(disposalType, isChecked))
            }
        )
        viewBinding.disposalTypes.adapter = disposalTypesAdapter
        return view
    }

    override fun overrideSystemBarColor(): SystemBarColor {
        return SystemBarColor.LIGHT
    }

    override fun render(viewState: CalendarSettingsViewState) {
        bindHeader(viewState.headerViewState, viewBinding.header)
        viewBinding.introduction.text = requireContext().getString(viewState.introductionKey)
        val disposalTypes = mutableMapOf<DisposalType, Boolean>()
        DisposalType.values().forEach {
            disposalTypes[it] = viewState.selectedDisposalTypes.contains(it)
        }
        disposalTypesAdapter.disposalTypes = disposalTypes
        disposalTypesAdapter.toggleCDReplaceable = requireContext().getString(viewState.disposalToggleCDReplaceableKey)
        disposalTypesAdapter.disposalImageCDReplaceable = requireContext().getString(viewState.disposalImageCDReplaceableKey)
        disposalTypesAdapter.notifyDataSetChanged()
    }
}