package ch.dreipol.multiplatform.reduxsample.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.recyclerview.widget.RecyclerView
import ch.dreipol.multiplatform.reduxsample.databinding.ViewToggleListItemBinding
import ch.dreipol.multiplatform.reduxsample.shared.database.DisposalType
import ch.dreipol.multiplatform.reduxsample.utils.getDrawableIdentifier
import ch.dreipol.multiplatform.reduxsample.utils.getString
import com.github.dreipol.dreidroid.utils.ViewUtils

class SelectDisposalTypesViewHolder(val disposalTypeListItemBinding: ViewToggleListItemBinding) :
    RecyclerView.ViewHolder(disposalTypeListItemBinding.root)

class SelectDisposalTypesAdapter(
    private val context: Context,
    var disposalTypes: Map<DisposalType, Boolean>,
    @ColorRes val textColor: Int,
    private val onCheckedChange: (isChecked: Boolean, disposalType: DisposalType) -> Unit
) :
    RecyclerView.Adapter<SelectDisposalTypesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectDisposalTypesViewHolder {
        return SelectDisposalTypesViewHolder(ViewToggleListItemBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: SelectDisposalTypesViewHolder, position: Int) {
        ViewUtils.useTouchDownListener(holder.disposalTypeListItemBinding.root, holder.disposalTypeListItemBinding.root)
        val item = disposalTypes.entries.toList()[position]
        holder.disposalTypeListItemBinding.text.setTextColor(context.resources.getColor(textColor, null))
        holder.disposalTypeListItemBinding.text.text = context.getString(item.key.translationKey)
        holder.disposalTypeListItemBinding.icon.setImageResource(context.getDrawableIdentifier(item.key.iconId))
        holder.disposalTypeListItemBinding.toggle.setOnCheckedChangeListener { _, _ -> }
        holder.disposalTypeListItemBinding.toggle.isChecked = item.value
        holder.disposalTypeListItemBinding.toggle.setOnCheckedChangeListener { _, isChecked ->
            onCheckedChange.invoke(isChecked, item.key)
        }
        holder.disposalTypeListItemBinding.root.setOnClickListener {
            holder.disposalTypeListItemBinding.toggle.toggle()
        }
        val separatorVisibility = if (disposalTypes.size - 1 == position) View.GONE else View.VISIBLE
        holder.disposalTypeListItemBinding.separator.visibility = separatorVisibility
    }

    override fun getItemCount(): Int {
        return disposalTypes.size
    }
}