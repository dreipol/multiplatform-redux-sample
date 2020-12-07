package ch.dreipol.multiplatform.reduxsample.fragments.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ch.dreipol.dreimultiplatform.reduxkotlin.rootDispatch
import ch.dreipol.multiplatform.reduxsample.R
import ch.dreipol.multiplatform.reduxsample.databinding.FragmentOnboardingSelectDisposalTypesBinding
import ch.dreipol.multiplatform.reduxsample.shared.database.DisposalType
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.UpdateShowDisposalType
import ch.dreipol.multiplatform.reduxsample.shared.ui.BaseOnboardingSubState
import ch.dreipol.multiplatform.reduxsample.shared.ui.SelectDisposalTypesState
import ch.dreipol.multiplatform.reduxsample.view.SelectDisposalTypesAdapter

class SelectDisposalTypesFragment : OnboardingFragment() {

    private lateinit var selectDisposalTypesBinding: FragmentOnboardingSelectDisposalTypesBinding
    private lateinit var selectDisposalTypesAdapter: SelectDisposalTypesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        selectDisposalTypesBinding = viewBinding.fragmentOnboardingSelectDisposalTypes
        selectDisposalTypesBinding.root.visibility = View.VISIBLE
        selectDisposalTypesAdapter =
            SelectDisposalTypesAdapter(
                requireContext(), emptyMap(), R.color.test_app_white,
                { isChecked, disposalType ->
                    rootDispatch(UpdateShowDisposalType(disposalType, isChecked))
                },
                R.dimen.onboarding_button_container_height
            )
        selectDisposalTypesBinding.disposalTypes.adapter = selectDisposalTypesAdapter
        return view
    }

    override fun render(onboardingSubState: BaseOnboardingSubState) {
        if (onboardingSubState !is SelectDisposalTypesState) return
        super.render(onboardingSubState)
        val data = mutableMapOf<DisposalType, Boolean>()
        DisposalType.values().forEach {
            data[it] = onboardingSubState.selectedDisposalTypes.contains(it)
        }
        selectDisposalTypesAdapter.disposalTypes = data
        selectDisposalTypesAdapter.notifyDataSetChanged()
    }
}