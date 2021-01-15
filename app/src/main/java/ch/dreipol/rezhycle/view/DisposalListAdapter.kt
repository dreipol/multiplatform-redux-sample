package ch.dreipol.rezhycle.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import ch.dreipol.dreimultiplatform.reduxkotlin.rootDispatch
import ch.dreipol.multiplatform.reduxsample.shared.redux.thunk.addOrRemoveNotificationThunk
import ch.dreipol.multiplatform.reduxsample.shared.ui.DisposalCalendarEntry
import ch.dreipol.multiplatform.reduxsample.shared.ui.DisposalCalendarMonth
import ch.dreipol.rezhycle.databinding.ViewDisposalGroupItemBinding
import ch.dreipol.rezhycle.databinding.ViewDisposalListItemBinding
import ch.dreipol.rezhycle.utils.getDrawableIdentifier
import ch.dreipol.rezhycle.utils.getString
import com.github.dreipol.dreidroid.components.GroupedListAdapter
import com.github.dreipol.dreidroid.utils.ViewUtils

class DisposalListAdapter(var disposalCalendarEntry: List<DisposalCalendarMonth>, private val context: Context) :
    GroupedListAdapter<DisposalCalendarEntry, String, String, ViewDisposalGroupItemBinding, ViewDisposalListItemBinding>() {
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
        ViewUtils.useTouchDownListener(binding.bell, binding.itemContainer)
        binding.bell.setOnClickListener { rootDispatch(addOrRemoveNotificationThunk(model.disposal.disposalType)) }
        binding.bell.setImageResource(context.getDrawableIdentifier(model.notificationIconId))
        binding.date.text = model.buildTimeString { context.getString(it) }
        binding.icon.setImageResource(context.getDrawableIdentifier(model.disposal.disposalType.iconId))
        binding.text.text = context.getString(model.disposal.disposalType.translationKey)
    }
}