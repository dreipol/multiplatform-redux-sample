package ch.dreipol.multiplatform.reduxsample.shared.redux.actions

import ch.dreipol.multiplatform.reduxsample.shared.database.Reminder
import ch.dreipol.multiplatform.reduxsample.shared.delight.NotificationSettings
import ch.dreipol.multiplatform.reduxsample.shared.delight.Settings

data class SettingsInitializedAction(
    val settings: Settings?,
    val notificationSettings: List<NotificationSettings>,
    val nextReminder: Reminder?
) : Action