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
import ch.dreipol.rezhycle.databinding.ViewIconListItemBinding
import com.github.dreipol.dreidroid.utils.ViewUtils

class SelectItemListViewHolder(val binding: ViewIconListItemBinding) : RecyclerView.ViewHolder(binding.root)

class SelectItemListAdapter<Item>(
    var items: List<Pair<Item, Boolean>>,
    var checkIconCD: String,
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
        holder.binding.root.setOnClickListener { onItemClicked(item.first) }
        holder.binding.text.text = description(item.first)
        holder.binding.icon.visibility = if (item.second) View.VISIBLE else View.INVISIBLE
        holder.binding.icon.contentDescription = checkIconCD
        val separatorVisibility = if (items.last() == item) View.GONE else View.VISIBLE
        holder.binding.separator.visibility = separatorVisibility
    }

    override fun getItemCount(): Int {
        return items.size
    }
}