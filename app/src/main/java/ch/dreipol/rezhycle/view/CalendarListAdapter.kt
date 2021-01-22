package ch.dreipol.rezhycle.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import ch.dreipol.dreimultiplatform.reduxkotlin.rootDispatch
import ch.dreipol.multiplatform.reduxsample.shared.redux.addOrRemoveNotificationThunk
import ch.dreipol.multiplatform.reduxsample.shared.ui.DisposalCalendarEntry
import ch.dreipol.multiplatform.reduxsample.shared.ui.DisposalCalendarMonth
import ch.dreipol.rezhycle.databinding.ViewCalendarHeaderBinding
import ch.dreipol.rezhycle.databinding.ViewDisposalGroupItemBinding
import ch.dreipol.rezhycle.databinding.ViewDisposalListItemBinding
import ch.dreipol.rezhycle.utils.getDrawableIdentifier
import ch.dreipol.rezhycle.utils.getString
import com.github.dreipol.dreidroid.components.GroupedListAdapter
import com.github.dreipol.dreidroid.utils.ViewUtils

class CalendarListAdapter(
    var calendarHeaderModel: CalendarHeaderModel,
    var disposalCalendarEntry: List<DisposalCalendarMonth>,
    var bellCDReplaceable: String,
    var disposalImageCDReplaceable: String,
    private val context: Context
) : GroupedListAdapter<DisposalCalendarEntry, String, String, ViewDisposalGroupItemBinding, ViewDisposalListItemBinding>() {

    companion object {
        const val CALENDAR_HEADER_VIEW_TYPE = 3
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupedListViewHolder {
        if (viewType == CALENDAR_HEADER_VIEW_TYPE) {
            return GroupedListViewHolder(ViewCalendarHeaderBinding.inflate(LayoutInflater.from(context), parent, false))
        }
        return super.onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: GroupedListViewHolder, position: Int) {
        if (position == 0) {
            val binding = holder.binding as ViewCalendarHeaderBinding
            configureCalendarHeaderBinding(binding)
            return
        }
        super.onBindViewHolder(holder, position - 1)
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + 1
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return CALENDAR_HEADER_VIEW_TYPE
        }
        return super.getItemViewType(position - 1)
    }

    override fun getSortComperator(): Comparator<DisposalCalendarEntry> {
        // keep order
        return Comparator { _, _ -> 0 }
    }

    override fun getData(): List<DisposalCalendarEntry> {
        return disposalCalendarEntry.flatMap { it.disposalCalendarEntries }
    }

    override fun getGroupByProperty(dataModel: DisposalCalendarEntry): String {
        return disposalCalendarEntry.first { it.disposalCalendarEntries.contains(dataModel) }.formattedHeader
    }

    override fun getHeaderModel(dataModel: DisposalCalendarEntry): String {
        return disposalCalendarEntry.first { it.disposalCalendarEntries.contains(dataModel) }.formattedHeader
    }

    override fun createHeaderBinding(parent: ViewGroup): ViewDisposalGroupItemBinding {
        return ViewDisposalGroupItemBinding.inflate(LayoutInflater.from(context), parent, false)
    }

    override fun createDataItemBinding(parent: ViewGroup): ViewDisposalListItemBinding {
        return ViewDisposalListItemBinding.inflate(LayoutInflater.from(context), parent, false)
    }

    override fun configureHeaderBinding(binding: ViewDisposalGroupItemBinding, model: String) {
        binding.text.text = model
    }

    override fun configureDataItemBinding(binding: ViewDisposalListItemBinding, model: DisposalCalendarEntry) {
        val disposalText = context.getString(model.disposal.disposalType.translationKey)
        ViewUtils.useTouchDownListener(binding.bell, binding.itemContainer)
        binding.bell.setOnClickListener { rootDispatch(addOrRemoveNotificationThunk(model.disposal.disposalType)) }
        binding.bell.setImageResource(context.getDrawableIdentifier(model.notificationIconId))
        binding.bell.contentDescription = String.format(bellCDReplaceable, disposalText)
        binding.date.text = model.buildTimeString { context.getString(it) }
        binding.icon.setImageResource(context.getDrawableIdentifier(model.disposal.disposalType.iconId))
        binding.icon.contentDescription = String.format(disposalImageCDReplaceable, disposalText)
        binding.text.text = disposalText
    }

    private fun configureCalendarHeaderBinding(binding: ViewCalendarHeaderBinding) {
        binding.title.text = calendarHeaderModel.title
        binding.nextDisposals.adapter = calendarHeaderModel.adapter
    }
}

data class CalendarHeaderModel(val title: String, val adapter: NextDisposalListAdapter)