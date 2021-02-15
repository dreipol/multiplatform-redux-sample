package ch.dreipol.multiplatform.reduxsample.shared.redux.actions

import ch.dreipol.dreimultiplatform.reduxkotlin.permissions.NotificationPermission
import ch.dreipol.multiplatform.reduxsample.shared.database.Reminder
import ch.dreipol.multiplatform.reduxsample.shared.delight.NotificationSettings
import ch.dreipol.multiplatform.reduxsample.shared.delight.Settings

data class SettingsInitializedAction(
    val settings: Settings?,
    val notificationPermission: NotificationPermission,
    val notificationSettings: List<NotificationSettings>,
    val nextReminders: List<Reminder>
) : Action