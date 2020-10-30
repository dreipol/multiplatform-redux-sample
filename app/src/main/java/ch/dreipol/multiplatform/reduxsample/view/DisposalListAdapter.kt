package ch.dreipol.multiplatform.reduxsample.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import ch.dreipol.dreimultiplatform.reduxkotlin.rootDispatch
import ch.dreipol.multiplatform.reduxsample.databinding.ViewDisposalGroupItemBinding
import ch.dreipol.multiplatform.reduxsample.databinding.ViewDisposalListItemBinding
import ch.dreipol.multiplatform.reduxsample.shared.redux.addOrRemoveNotificationThunk
import ch.dreipol.multiplatform.reduxsample.shared.ui.DisposalNotification
import ch.dreipol.multiplatform.reduxsample.utils.getDrawableIdentifier
import ch.dreipol.multiplatform.reduxsample.utils.getString
import com.github.dreipol.dreidroid.components.GroupedListAdapter

class DisposalListAdapter(var disposalNotification: List<DisposalNotification>, private val context: Context) :
    GroupedListAdapter<DisposalNotification, String, String, ViewDisposalGroupItemBinding, ViewDisposalListItemBinding>() {
    override fun getSortComperator(): Comparator<DisposalNotification> {
        return Comparator { disposal1, disposal2 -> disposal1.disposal.date.compareTo(disposal2.disposal.date) }
    }

    override fun getData(): List<DisposalNotification> {
        return disposalNotification
    }

    override fun getGroupByProperty(dataModel: DisposalNotification): String {
        return dataModel.formattedHeader
    }

    override fun getHeaderModel(dataModel: DisposalNotification): String {
        return dataModel.formattedHeader
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

    override fun configureDataItemBinding(binding: ViewDisposalListItemBinding, model: DisposalNotification) {
        binding.bell.setOnClickListener { rootDispatch(addOrRemoveNotificationThunk(model.disposal.disposalType)) }
        binding.bell.setImageResource(context.getDrawableIdentifier(model.notificationIconId))
        binding.date.text = model.formattedDate
        binding.icon.setImageResource(context.getDrawableIdentifier(model.disposal.disposalType.iconId))
        binding.text.text = context.getString(model.disposal.disposalType.translationKey)
    }
}