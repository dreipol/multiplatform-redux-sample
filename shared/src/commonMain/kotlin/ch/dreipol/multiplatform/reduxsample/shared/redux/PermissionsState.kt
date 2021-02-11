package ch.dreipol.multiplatform.reduxsample.shared.redux

import ch.dreipol.dreimultiplatform.reduxkotlin.permissions.NotificationPermission
import ch.dreipol.multiplatform.reduxsample.shared.utils.SettingsHelper


data class PermissionsState(val notificationPermission: NotificationPermission = NotificationPermission.NOT_DETERMINED) {
    companion object {
        fun fromSettingsOrDefault(): PermissionsState {
            val notificationPermission: NotificationPermission =
                SettingsHelper.notificationPermission?.let { NotificationPermission.fromInt(it) } ?: NotificationPermission.NOT_DETERMINED
            return PermissionsState(notificationPermission)
        }
    }
}