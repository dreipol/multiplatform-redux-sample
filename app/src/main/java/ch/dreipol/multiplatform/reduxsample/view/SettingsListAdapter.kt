package ch.dreipol.multiplatform.reduxsample.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ch.dreipol.multiplatform.reduxsample.R
import ch.dreipol.multiplatform.reduxsample.databinding.ViewIconListItemBinding
import ch.dreipol.multiplatform.reduxsample.utils.getString

class SettingsListViewHolder(val binding: ViewIconListItemBinding) : RecyclerView.ViewHolder(binding.root)

class SettingsListAdapter(var settings: List<String>, private val context: Context) : RecyclerView.Adapter<SettingsListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingsListViewHolder {
        val binding = ViewIconListItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return SettingsListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SettingsListViewHolder, position: Int) {
        holder.binding.root.setBackgroundResource(R.color.transparent_clickable_background)
        holder.binding.root.setOnClickListener { } // TODO
        holder.binding.icon.setImageResource(R.drawable.ic_36_chevron_right)
        holder.binding.text.setTextColor(context.resources.getColor(R.color.test_app_blue, null))
        holder.binding.text.text = context.getString(settings[position])
    }

    override fun getItemCount(): Int {
        return settings.size
    }
}