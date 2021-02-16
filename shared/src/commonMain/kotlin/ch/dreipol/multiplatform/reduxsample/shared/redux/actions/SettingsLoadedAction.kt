package ch.dreipol.multiplatform.reduxsample.shared.redux.actions

import ch.dreipol.dreimultiplatform.reduxkotlin.permissions.NotificationPermission
import ch.dreipol.multiplatform.reduxsample.shared.delight.NotificationSettings
import ch.dreipol.multiplatform.reduxsample.shared.delight.Settings

data class SettingsLoadedAction(
    val settings: Settings,
    val notificationSettings: List<NotificationSettings>,
    val notificationPermission: NotificationPermission
) : Action