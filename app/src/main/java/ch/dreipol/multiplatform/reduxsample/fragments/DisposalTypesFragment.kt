package ch.dreipol.multiplatform.reduxsample.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ch.dreipol.dreimultiplatform.reduxkotlin.PresenterLifecycleObserver
import ch.dreipol.dreimultiplatform.reduxkotlin.rootDispatch
import ch.dreipol.multiplatform.reduxsample.R
import ch.dreipol.multiplatform.reduxsample.databinding.FragmentDisposalTypesBinding
import ch.dreipol.multiplatform.reduxsample.shared.database.DisposalType
import ch.dreipol.multiplatform.reduxsample.shared.redux.updateShowDisposalType
import ch.dreipol.multiplatform.reduxsample.shared.ui.DisposalTypesView
import ch.dreipol.multiplatform.reduxsample.shared.ui.DisposalTypesViewState
import ch.dreipol.multiplatform.reduxsample.utils.getString
import ch.dreipol.multiplatform.reduxsample.view.SelectDisposalTypesAdapter

class DisposalTypesFragment : BaseFragment<FragmentDisposalTypesBinding, DisposalTypesView>(), DisposalTypesView {

    private lateinit var disposalTypesAdapter: SelectDisposalTypesAdapter

    override val presenterObserver = PresenterLifecycleObserver(this)

    override fun createBinding(): FragmentDisposalTypesBinding {
        return FragmentDisposalTypesBinding.inflate(layoutInflater)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        disposalTypesAdapter = SelectDisposalTypesAdapter(requireContext(), emptyMap(), R.color.test_app_blue) { isChecked, disposalType ->
            rootDispatch(updateShowDisposalType(disposalType, isChecked))
        }
        viewBinding.disposalTypes.adapter = disposalTypesAdapter
        return view
    }

    override fun render(viewState: DisposalTypesViewState) {
        bindHeader(viewState.headerViewState, viewBinding.header)
        viewBinding.description.text = requireContext().getString(viewState.description)
        val disposalTypes = mutableMapOf<DisposalType, Boolean>()
        DisposalType.values().forEach {
            disposalTypes[it] = viewState.selectedDisposalTypes.contains(it)
        }
        disposalTypesAdapter.disposalTypes = disposalTypes
        disposalTypesAdapter.notifyDataSetChanged()
    }
}