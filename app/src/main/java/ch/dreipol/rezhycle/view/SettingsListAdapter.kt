package ch.dreipol.rezhycle.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ch.dreipol.dreimultiplatform.reduxkotlin.rootDispatch
import ch.dreipol.multiplatform.reduxsample.shared.ui.SettingsEntry
import ch.dreipol.rezhycle.R
import ch.dreipol.rezhycle.databinding.ViewIconListItemBinding
import ch.dreipol.rezhycle.utils.getString
import com.github.dreipol.dreidroid.utils.ViewUtils

class SettingsListViewHolder(val binding: ViewIconListItemBinding) : RecyclerView.ViewHolder(binding.root)

class SettingsListAdapter(var settings: List<SettingsEntry>, private val context: Context) :
    RecyclerView.Adapter<SettingsListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingsListViewHolder {
        val binding = ViewIconListItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return SettingsListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SettingsListViewHolder, position: Int) {
        val settingsEntry = settings[position]
        ViewUtils.useTouchDownListener(holder.binding.root, holder.binding.root)
        holder.binding.root.backgroundTintList = context.getColorStateList(R.color.transparent_clickable_background)
        holder.binding.root.setOnClickListener { rootDispatch(settingsEntry.navigationAction) }
        holder.binding.icon.setImageResource(R.drawable.ic_36_chevron_right)
        holder.binding.text.setTextColor(context.resources.getColor(R.color.test_app_blue, null))
        holder.binding.text.text = context.getString(settingsEntry.descriptionKey)
        val separatorVisibility = if (settings.last() == settingsEntry) View.GONE else View.VISIBLE
        holder.binding.separator.visibility = separatorVisibility
    }

    override fun getItemCount(): Int {
        return settings.size
    }
}