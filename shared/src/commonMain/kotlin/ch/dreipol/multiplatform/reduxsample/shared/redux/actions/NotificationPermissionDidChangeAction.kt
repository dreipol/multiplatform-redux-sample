package ch.dreipol.multiplatform.reduxsample.shared.redux.actions

import ch.dreipol.dreimultiplatform.reduxkotlin.permissions.NotificationPermission

data class NotificationPermissionDidChangeAction(val value: NotificationPermission) : Action