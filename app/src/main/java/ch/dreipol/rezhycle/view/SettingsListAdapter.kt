/*
 * Copyright (c) 2021 dreipol GmbH
 *
 * Licensed under a Creative Commons Attribution-NonCommercial 4.0 International License. A copy of the license is provided with this code.
 *
 * No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
 */

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

class SettingsListAdapter(var settings: List<SettingsEntry>, private val context: Context, var chevronRightCD: String = "") :
    RecyclerView.Adapter<SettingsListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingsListViewHolder {
        val binding = ViewIconListItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return SettingsListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SettingsListViewHolder, position: Int) {
        val settingsEntry = settings[position]
        ViewUtils.useTouchDownListener(holder.binding.root, holder.binding.root)
        holder.binding.root.setOnClickListener { rootDispatch(settingsEntry.navigationAction) }
        holder.binding.icon.setImageResource(R.drawable.ic_36_chevron_right)
        holder.binding.icon.contentDescription = chevronRightCD
        holder.binding.text.text = context.getString(settingsEntry.descriptionKey)
        val separatorVisibility = if (settings.last() == settingsEntry) View.GONE else View.VISIBLE
        holder.binding.separator.visibility = separatorVisibility
    }

    override fun getItemCount(): Int {
        return settings.size
    }
}