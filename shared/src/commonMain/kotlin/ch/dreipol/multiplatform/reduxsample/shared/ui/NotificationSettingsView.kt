package ch.dreipol.multiplatform.reduxsample.shared.ui

import ch.dreipol.multiplatform.reduxsample.shared.database.DisposalType
import ch.dreipol.multiplatform.reduxsample.shared.database.RemindTime
import ch.dreipol.multiplatform.reduxsample.shared.database.SettingsDataStore

data class NotificationSettingsViewState(
    val notificationEnabled: Boolean = false,
    val remindTimes: List<Pair<RemindTime, Boolean>> = RemindTime.values()
        .map { if (SettingsDataStore.defaultRemindTime == it) it to true else it to false },
    val selectedDisposalTypes: List<DisposalType> = DisposalType.values().toList(),
    val headerViewState: HeaderViewState = HeaderViewState("settings_notifications"),
    val introductionKey: String = "settings_notification_description",
    val notificationToggleCDKey: String = "notification_toggle_contentdescription",
    val checkIconCDKey: String = "check_icon_contentdescription",
    val disposalToggleCDReplaceableKey: String = "disposal_toggle_contentdescription",
    val disposalImageCDReplaceableKey: String = "disposal_image_contentdescription",
)

interface NotificationSettingsView : BaseView {
    override fun presenter() = notificationSettingsPresenter

    fun render(notificationSettingsViewState: NotificationSettingsViewState)
}

val notificationSettingsPresenter = presenter<NotificationSettingsView> {
    {
        select({ it.settingsViewState.notificationSettingsViewState }) {
            render(state.settingsViewState.notificationSettingsViewState)
        }
    }
}