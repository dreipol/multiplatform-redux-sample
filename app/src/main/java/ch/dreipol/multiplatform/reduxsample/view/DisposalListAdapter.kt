package ch.dreipol.multiplatform.reduxsample.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import ch.dreipol.dreimultiplatform.reduxkotlin.rootDispatch
import ch.dreipol.multiplatform.reduxsample.databinding.ViewDisposalGroupItemBinding
import ch.dreipol.multiplatform.reduxsample.databinding.ViewDisposalListItemBinding
import ch.dreipol.multiplatform.reduxsample.shared.redux.addOrRemoveNotificationThunk
import ch.dreipol.multiplatform.reduxsample.shared.ui.DisposalCalendarEntry
import ch.dreipol.multiplatform.reduxsample.utils.getDrawableIdentifier
import ch.dreipol.multiplatform.reduxsample.utils.getString
import com.github.dreipol.dreidroid.components.GroupedListAdapter
import com.github.dreipol.dreidroid.utils.ViewUtils

class DisposalListAdapter(var disposalCalendarEntry: Map<String, List<DisposalCalendarEntry>>, private val context: Context) :
    GroupedListAdapter<Pair<String, DisposalCalendarEntry>, String, String, ViewDisposalGroupItemBinding, ViewDisposalListItemBinding>() {
    override fun getSortComperator(): Comparator<Pair<String, DisposalCalendarEntry>> {
        // keep order
        return Comparator { _, _ -> 0 }
    }

    override fun getData(): List<Pair<String, DisposalCalendarEntry>> {
        return disposalCalendarEntry.flatMap { mapEntry -> mapEntry.value.map { mapEntry.key to it } }
    }

    override fun getGroupByProperty(dataModel: Pair<String, DisposalCalendarEntry>): String {
        return dataModel.first
    }

    override fun getHeaderModel(dataModel: Pair<String, DisposalCalendarEntry>): String {
        return dataModel.first
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

    override fun configureDataItemBinding(binding: ViewDisposalListItemBinding, model: Pair<String, DisposalCalendarEntry>) {
        ViewUtils.useTouchDownListener(binding.bell, binding.itemContainer)
        val dataItem = model.second
        binding.bell.setOnClickListener { rootDispatch(addOrRemoveNotificationThunk(dataItem.disposal.disposalType)) }
        binding.bell.setImageResource(context.getDrawableIdentifier(dataItem.notificationIconId))
        binding.date.text = dataItem.buildTimeString { context.getString(it) }
        binding.icon.setImageResource(context.getDrawableIdentifier(dataItem.disposal.disposalType.iconId))
        binding.text.text = context.getString(dataItem.disposal.disposalType.translationKey)
    }
}