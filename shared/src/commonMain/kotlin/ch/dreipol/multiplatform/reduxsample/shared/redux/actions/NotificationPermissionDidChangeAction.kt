package ch.dreipol.multiplatform.reduxsample.shared.redux.actions

import ch.dreipol.dreimultiplatform.reduxkotlin.permissions.NotificationPermission

data class NotificationPermissionDidChangeAction(private val rawValue: Int) : Action {
    val value = NotificationPermission.fromInt(rawValue)
}