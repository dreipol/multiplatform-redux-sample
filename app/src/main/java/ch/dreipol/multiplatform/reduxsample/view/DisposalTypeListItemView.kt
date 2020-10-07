package ch.dreipol.multiplatform.reduxsample.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import ch.dreipol.multiplatform.reduxsample.databinding.ViewDisposalTypeListItemBinding

class DisposalTypeListItemView(context: Context) : LinearLayout(context) {

    private val binding = ViewDisposalTypeListItemBinding.inflate(LayoutInflater.from(context))

    fun init(icon: Int?, text: String, selected: Boolean) {
        if (icon == null) {
            binding.icon.visibility = View.GONE
        } else {
            binding.icon.visibility = View.VISIBLE
            binding.icon.setImageResource(icon)
        }
        binding.text.text = text
        binding.toggle.isActivated = selected
    }
}