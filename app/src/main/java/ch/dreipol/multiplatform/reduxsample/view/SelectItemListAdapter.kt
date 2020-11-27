package ch.dreipol.multiplatform.reduxsample.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ch.dreipol.multiplatform.reduxsample.R
import ch.dreipol.multiplatform.reduxsample.databinding.ViewIconListItemBinding
import com.github.dreipol.dreidroid.utils.ViewUtils

class SelectItemListViewHolder(val binding: ViewIconListItemBinding) : RecyclerView.ViewHolder(binding.root)

class SelectItemListAdapter<Item>(
    var items: List<Pair<Item, Boolean>>,
    private val context: Context,
    private val description: (Item) -> String,
    private val onItemClicked: (Item) -> Unit
) :
    RecyclerView.Adapter<SelectItemListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectItemListViewHolder {
        val binding = ViewIconListItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return SelectItemListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SelectItemListViewHolder, position: Int) {
        val item = items[position]
        ViewUtils.useTouchDownListener(holder.binding.root, holder.binding.root)
        holder.binding.root.backgroundTintList = context.getColorStateList(R.color.transparent_clickable_background)
        holder.binding.root.setOnClickListener { onItemClicked.invoke(item.first) }
        holder.binding.text.setTextColor(context.resources.getColor(R.color.test_app_blue, null))
        holder.binding.text.text = description.invoke(item.first)
        holder.binding.icon.visibility = if (item.second) View.VISIBLE else View.INVISIBLE
    }

    override fun getItemCount(): Int {
        return items.size
    }
}