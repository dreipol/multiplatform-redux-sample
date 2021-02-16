package ch.dreipol.rezhycle.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ch.dreipol.multiplatform.reduxsample.shared.ui.DisposalCalendarEntry
import ch.dreipol.rezhycle.databinding.ViewNextDisposalListItemBinding
import ch.dreipol.rezhycle.utils.getDrawableIdentifier
import ch.dreipol.rezhycle.utils.getString

class NextDisposalViewHolder(val binding: ViewNextDisposalListItemBinding) : RecyclerView.ViewHolder(binding.root)

class NextDisposalListAdapter(
    var disposals: List<DisposalCalendarEntry>,
    private val context: Context,
    var disposalImageCDReplaceable: String = ""
) : RecyclerView.Adapter<NextDisposalViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NextDisposalViewHolder {
        return NextDisposalViewHolder(ViewNextDisposalListItemBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: NextDisposalViewHolder, position: Int) {
        val disposal = disposals[position]
        val disposalText = context.getString(disposal.disposal.disposalType.translationKey)
        val nextItemBinding = holder.binding
        nextItemBinding.icon.setImageResource(context.getDrawableIdentifier(disposal.disposal.disposalType.iconId))
        nextItemBinding.icon.contentDescription = String.format(disposalImageCDReplaceable, disposalText)
        nextItemBinding.date.text = disposal.buildTimeString { context.getString(it) }
        nextItemBinding.text.text = disposalText
        nextItemBinding.location.text = String.format(context.getString(disposal.locationReplaceable), disposal.disposal.zip)
    }

    override fun getItemCount(): Int {
        return disposals.size
    }
}