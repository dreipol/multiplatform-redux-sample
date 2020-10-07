package ch.dreipol.multiplatform.reduxsample.fragments.onboarding

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ch.dreipol.dreimultiplatform.reduxkotlin.rootDispatch
import ch.dreipol.multiplatform.reduxsample.databinding.FragmentOnboardingSelectDisposalTypesBinding
import ch.dreipol.multiplatform.reduxsample.databinding.ViewDisposalTypeListItemBinding
import ch.dreipol.multiplatform.reduxsample.shared.database.DisposalType
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.UpdateShowDisposalType
import ch.dreipol.multiplatform.reduxsample.shared.ui.BaseOnboardingSubState
import ch.dreipol.multiplatform.reduxsample.shared.ui.SelectDisposalTypesState
import ch.dreipol.multiplatform.reduxsample.utils.getDrawableIdentifier
import ch.dreipol.multiplatform.reduxsample.utils.getString

class SelectDisposalTypesFragment : OnboardingFragment() {

    private lateinit var selectDisposalTypesBinding: FragmentOnboardingSelectDisposalTypesBinding
    private lateinit var selectDisposalTypesAdapter: SelectDisposalTypesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        selectDisposalTypesBinding = viewBinding.fragmentOnboardingSelectDisposalTypes
        selectDisposalTypesBinding.root.visibility = View.VISIBLE
        selectDisposalTypesAdapter = SelectDisposalTypesAdapter(requireContext(), emptyMap())
        selectDisposalTypesBinding.disposalTypes.adapter = selectDisposalTypesAdapter
        return view
    }

    override fun render(onboardingSubState: BaseOnboardingSubState) {
        super.render(onboardingSubState)
        if (onboardingSubState !is SelectDisposalTypesState) return
        val data = mutableMapOf<DisposalType, Boolean>()
        data[DisposalType.CARTON] = onboardingSubState.showCarton
        data[DisposalType.BIO_WASTE] = onboardingSubState.showBioWaste
        data[DisposalType.PAPER] = onboardingSubState.showPaper
        data[DisposalType.E_TRAM] = onboardingSubState.showETram
        data[DisposalType.CARGO_TRAM] = onboardingSubState.showCargoTram
        data[DisposalType.TEXTILES] = onboardingSubState.showTextiles
        data[DisposalType.HAZARDOUS_WASTE] = onboardingSubState.showHazardousWaste
        data[DisposalType.SWEEPINGS] = onboardingSubState.showSweepings
        selectDisposalTypesAdapter.disposalTypes = data
        selectDisposalTypesAdapter.notifyDataSetChanged()
    }
}

class SelectDisposalTypesViewHolder(val disposalTypeListItemBinding: ViewDisposalTypeListItemBinding) :
    RecyclerView.ViewHolder(disposalTypeListItemBinding.root)

class SelectDisposalTypesAdapter(private val context: Context, var disposalTypes: Map<DisposalType, Boolean>) :
    RecyclerView.Adapter<SelectDisposalTypesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectDisposalTypesViewHolder {
        return SelectDisposalTypesViewHolder(ViewDisposalTypeListItemBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: SelectDisposalTypesViewHolder, position: Int) {
        val item = disposalTypes.entries.toList()[position]
        holder.disposalTypeListItemBinding.text.text = context.getString(item.key.translationKey)
        holder.disposalTypeListItemBinding.icon.setImageResource(context.getDrawableIdentifier(item.key.iconId))
        holder.disposalTypeListItemBinding.toggle.setOnCheckedChangeListener { _, _ -> }
        holder.disposalTypeListItemBinding.toggle.isChecked = item.value
        holder.disposalTypeListItemBinding.toggle.setOnCheckedChangeListener { _, isChecked ->
            rootDispatch(UpdateShowDisposalType(item.key, isChecked))
        }
    }

    override fun getItemCount(): Int {
        return disposalTypes.size
    }
}