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
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import ch.dreipol.multiplatform.reduxsample.shared.database.DisposalType
import ch.dreipol.rezhycle.databinding.ViewToggleListItemBinding
import ch.dreipol.rezhycle.utils.getDrawableIdentifier
import ch.dreipol.rezhycle.utils.getString
import com.github.dreipol.dreidroid.utils.ViewUtils

class SelectDisposalTypesViewHolder(val disposalTypeListItemBinding: ViewToggleListItemBinding) :
    RecyclerView.ViewHolder(disposalTypeListItemBinding.root)

class SelectDisposalTypesAdapter(
    private val context: Context,
    var disposalTypes: Map<DisposalType, Boolean>,
    @ColorRes val textColor: Int,
    private val onCheckedChange: (isChecked: Boolean, disposalType: DisposalType) -> Unit,
    @DimenRes val extraBottomSpaceLastItem: Int? = null,
    var toggleCDReplaceable: String = "",
    var disposalImageCDReplaceable: String = "",
) :
    RecyclerView.Adapter<SelectDisposalTypesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectDisposalTypesViewHolder {
        return SelectDisposalTypesViewHolder(ViewToggleListItemBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: SelectDisposalTypesViewHolder, position: Int) {
        ViewUtils.useTouchDownListener(holder.disposalTypeListItemBinding.root, holder.disposalTypeListItemBinding.root)
        val item = disposalTypes.entries.toList()[position]
        val itemText = context.getString(item.key.translationKey)
        holder.disposalTypeListItemBinding.text.setTextColor(context.resources.getColor(textColor, null))
        holder.disposalTypeListItemBinding.text.text = itemText
        holder.disposalTypeListItemBinding.icon.setImageResource(context.getDrawableIdentifier(item.key.iconId))
        holder.disposalTypeListItemBinding.icon.contentDescription = String.format(disposalImageCDReplaceable, itemText)
        holder.disposalTypeListItemBinding.toggle.setOnCheckedChangeListener { _, _ -> }
        holder.disposalTypeListItemBinding.toggle.isChecked = item.value
        holder.disposalTypeListItemBinding.toggle.setOnCheckedChangeListener { _, isChecked ->
            onCheckedChange(isChecked, item.key)
        }
        holder.disposalTypeListItemBinding.toggle.contentDescription = String.format(toggleCDReplaceable, itemText)
        holder.disposalTypeListItemBinding.root.setOnClickListener {
            holder.disposalTypeListItemBinding.toggle.toggle()
        }
        styleLastItem(position, holder)
    }

    private fun styleLastItem(position: Int, holder: SelectDisposalTypesViewHolder) {
        val isLastItem = disposalTypes.size - 1 == position
        val separatorVisibility = if (isLastItem) View.GONE else View.VISIBLE
        holder.disposalTypeListItemBinding.separator.visibility = separatorVisibility
        extraBottomSpaceLastItem?.let {
            val marginBottom = if (isLastItem) context.resources.getDimensionPixelOffset(it) else 0
            holder.disposalTypeListItemBinding.root.updateLayoutParams<RecyclerView.LayoutParams> { bottomMargin = marginBottom }
        }
    }

    override fun getItemCount(): Int {
        return disposalTypes.size
    }
}