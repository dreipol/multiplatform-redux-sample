package ch.dreipol.multiplatform.reduxsample.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ch.dreipol.dreimultiplatform.reduxkotlin.rootDispatch
import ch.dreipol.multiplatform.reduxsample.databinding.ViewDisposalTypeListItemBinding
import ch.dreipol.multiplatform.reduxsample.shared.database.DisposalType
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.UpdateShowDisposalType
import ch.dreipol.multiplatform.reduxsample.utils.getDrawableIdentifier
import ch.dreipol.multiplatform.reduxsample.utils.getString

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