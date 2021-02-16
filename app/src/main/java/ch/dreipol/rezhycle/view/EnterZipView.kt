package ch.dreipol.rezhycle.view

import android.app.Activity
import android.content.Context
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import androidx.annotation.ColorRes
import androidx.core.widget.addTextChangedListener
import ch.dreipol.dreimultiplatform.reduxkotlin.rootDispatch
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.ZipUpdatedAction
import ch.dreipol.multiplatform.reduxsample.shared.ui.EnterZipViewState
import ch.dreipol.rezhycle.R
import ch.dreipol.rezhycle.databinding.ViewEnterZipBinding
import ch.dreipol.rezhycle.showKeyboard
import ch.dreipol.rezhycle.utils.getString
import ch.dreipol.rezhycle.utils.setNewText

class EnterZipView(context: Context, attributeSet: AttributeSet) : LinearLayout(context, attributeSet) {

    private val possibleZipsAdapter = ArrayAdapter<String>(context, R.layout.view_dropdown_item, R.id.text, mutableListOf())
    private var textWatcher: TextWatcher? = null
    private val binding = ViewEnterZipBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        binding.zip.setAdapter(possibleZipsAdapter)
        binding.zip.setOnClickListener { binding.zip.showDropDown() }
        binding.zip.setDropDownBackgroundResource(R.drawable.round_dropdown_background)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        removeTextWatcher()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        addTextWatcher()
    }

    fun focus(activity: Activity) {
        binding.zip.post {
            binding.zip.requestFocus()
            activity.showKeyboard(binding.zip)
        }
    }

    fun setLabelColor(@ColorRes color: Int) {
        binding.label.setTextColor(context.getColor(color))
    }

    fun update(enterZipViewState: EnterZipViewState) {
        binding.label.text = context?.getString(enterZipViewState.enterZipLabel)
        removeTextWatcher()
        binding.zip.setNewText(enterZipViewState.selectedZip?.toString())
        addTextWatcher()
        possibleZipsAdapter.clear()
        possibleZipsAdapter.addAll(enterZipViewState.possibleZips.map { it.toString() })
        possibleZipsAdapter.notifyDataSetChanged()
    }

    private fun addTextWatcher() {
        textWatcher = binding.zip.addTextChangedListener(
            afterTextChanged = { text ->
                val newZip = text?.toString()?.toIntOrNull()
                rootDispatch(ZipUpdatedAction(newZip))
            }
        )
    }

    private fun removeTextWatcher() {
        textWatcher?.let {
            binding.zip.removeTextChangedListener(textWatcher)
            textWatcher = null
        }
    }
}