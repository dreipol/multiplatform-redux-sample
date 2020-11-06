package ch.dreipol.multiplatform.reduxsample.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ch.dreipol.dreimultiplatform.reduxkotlin.rootDispatch
import ch.dreipol.multiplatform.reduxsample.R
import ch.dreipol.multiplatform.reduxsample.databinding.ViewIconListItemBinding
import ch.dreipol.multiplatform.reduxsample.shared.ui.SettingsEntry
import ch.dreipol.multiplatform.reduxsample.utils.getString

class SettingsListViewHolder(val binding: ViewIconListItemBinding) : RecyclerView.ViewHolder(binding.root)

class SettingsListAdapter(var settings: List<SettingsEntry>, private val context: Context) :
    RecyclerView.Adapter<SettingsListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingsListViewHolder {
        val binding = ViewIconListItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return SettingsListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SettingsListViewHolder, position: Int) {
        val settingsEntry = settings[position]
        holder.binding.root.backgroundTintList = context.getColorStateList(R.color.transparent_clickable_background)
        holder.binding.root.setOnClickListener { rootDispatch(settingsEntry.navigationAction) }
        holder.binding.icon.setImageResource(R.drawable.ic_36_chevron_right)
        holder.binding.text.setTextColor(context.resources.getColor(R.color.test_app_blue, null))
        holder.binding.text.text = context.getString(settingsEntry.descriptionKey)
    }

    override fun getItemCount(): Int {
        return settings.size
    }
}