package ch.dreipol.multiplatform.reduxsample.shared.ui

import ch.dreipol.multiplatform.reduxsample.shared.delight.NotificationSettings

data class NotificationSettingsViewState(
    val headerViewState: HeaderViewState = HeaderViewState("settings_notifications"),
    val descriptionKey: String = "settings_notification_description"
)

interface NotificationSettingsView : BaseView {
    override fun presenter() = notificationSettingsPresenter

    fun render(notificationSettingsViewState: NotificationSettingsViewState, notificationSettings: List<NotificationSettings>?)
}

val notificationSettingsPresenter = presenter<NotificationSettingsView> {
    {
        val render = {
            render(state.settingsViewState.notificationSettingsViewState, state.settingsState.notificationSettings)
        }
        select({ it.settingsViewState.notificationSettingsViewState }) {
            render()
        }
        select({ it.settingsState }) {
            render()
        }
    }
}