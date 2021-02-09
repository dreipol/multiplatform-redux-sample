package ch.dreipol.rezhycle.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import ch.dreipol.dreimultiplatform.reduxkotlin.rootDispatch
import ch.dreipol.multiplatform.reduxsample.shared.database.RemindTime
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.UpdateAddNotification
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.UpdateRemindTime
import ch.dreipol.rezhycle.R
import ch.dreipol.rezhycle.databinding.ViewIconListItemBinding
import ch.dreipol.rezhycle.databinding.ViewToggleListItemBinding
import ch.dreipol.rezhycle.utils.getString
import com.github.dreipol.dreidroid.components.GroupedListAdapter
import com.github.dreipol.dreidroid.utils.ViewUtils

enum class NotificationListTheme(
    @ColorRes val textColor: Int,
    @ColorRes val secondaryColor: Int,
    @ColorRes val accentColor: Int,
) {
    DARK(R.color.white_color_state, R.color.secondary_color_state, R.color.accent_color_state),
    LIGHT(R.color.primary_dark_color_state, R.color.secondary_light_color_state, R.color.accent_color_state);
}

class NotificationListAdapter(
    private val context: Context,
    var remindTimes: List<Pair<RemindTime, Boolean>>,
    var notificationEnabled: Boolean,
    val theme: NotificationListTheme = NotificationListTheme.DARK,
    private val onRemindTimeSelected: (remindTime: RemindTime) -> Unit = { rootDispatch(UpdateRemindTime(it)) },
    private val onNotificationToggled: (notificationEnabled: Boolean) -> Unit = { rootDispatch(UpdateAddNotification(it)) },
    @DimenRes val extraBottomSpaceLastItem: Int? = null,
    var notificationToggleCD: String = "",
    var checkIconCD: String = ""
) :
    GroupedListAdapter<Pair<RemindTime, Boolean>, Boolean, Boolean, ViewToggleListItemBinding, ViewIconListItemBinding>() {

    private val textColor = context.resources.getColorStateList(theme.textColor, null)
    private val secondaryColor = context.resources.getColorStateList(theme.accentColor, null)

    override fun configureDataItemBinding(binding: ViewIconListItemBinding, model: Pair<RemindTime, Boolean>) {
        ViewUtils.useTouchDownListener(binding.root, binding.root)
        binding.root.isEnabled = notificationEnabled
        binding.text.isEnabled = notificationEnabled
        binding.icon.isEnabled = notificationEnabled
        binding.separator.isEnabled = notificationEnabled
        binding.separator.backgroundTintList = context.getColorStateList(theme.secondaryColor)
        binding.text.text = context.getString(model.first.descriptionKey)
        binding.text.setTextColor(textColor)
        binding.icon.visibility = if (model.second) View.VISIBLE else View.INVISIBLE
        binding.icon.imageTintList = secondaryColor
        binding.icon.contentDescription = checkIconCD
        binding.root.setOnClickListener { onRemindTimeSelected(model.first) }
        styleLastItem(binding, model)
    }

    override fun configureHeaderBinding(binding: ViewToggleListItemBinding, model: Boolean) {
        ViewUtils.useTouchDownListener(binding.root, binding.root)
        binding.toggle.setOnCheckedChangeListener { _, _ -> }
        binding.toggle.isChecked = model
        binding.toggle.contentDescription = notificationToggleCD
        binding.toggle.setOnCheckedChangeListener { _, isChecked -> onNotificationToggled(isChecked) }
        binding.separator.isEnabled = model
        binding.separator.backgroundTintList = context.getColorStateList(theme.secondaryColor)
        binding.text.setText(R.string.onboarding_pushes)
        binding.text.setTextColor(textColor)
        binding.icon.visibility = View.GONE
        binding.root.setOnClickListener {
            binding.toggle.toggle()
        }
    }

    override fun createDataItemBinding(parent: ViewGroup): ViewIconListItemBinding {
        return ViewIconListItemBinding.inflate(LayoutInflater.from(context), parent, false)
    }

    override fun createHeaderBinding(parent: ViewGroup): ViewToggleListItemBinding {
        return ViewToggleListItemBinding.inflate(LayoutInflater.from(context), parent, false)
    }

    override fun getData(): List<Pair<RemindTime, Boolean>> {
        return remindTimes
    }

    override fun getGroupByProperty(dataModel: Pair<RemindTime, Boolean>): Boolean {
        return true
    }

    override fun getHeaderModel(dataModel: Pair<RemindTime, Boolean>): Boolean {
        return notificationEnabled
    }

    override fun getSortComperator(): Comparator<Pair<RemindTime, Boolean>> {
        return Comparator { _, _ -> 0 }
    }

    private fun styleLastItem(binding: ViewIconListItemBinding, model: Pair<RemindTime, Boolean>) {
        val isLastItem = remindTimes.last() == model
        val separatorVisibility = if (isLastItem && theme == NotificationListTheme.LIGHT) View.GONE else View.VISIBLE
        binding.separator.visibility = separatorVisibility
        extraBottomSpaceLastItem?.let {
            val marginBottom = if (isLastItem) context.resources.getDimensionPixelOffset(it) else 0
            binding.root.updateLayoutParams<RecyclerView.LayoutParams> { bottomMargin = marginBottom }
        }
    }
}