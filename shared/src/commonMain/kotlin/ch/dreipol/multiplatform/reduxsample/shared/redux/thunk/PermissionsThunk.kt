package ch.dreipol.multiplatform.reduxsample.shared.redux.thunk

import ch.dreipol.dreimultiplatform.kermit
import ch.dreipol.dreimultiplatform.reduxkotlin.permissions.NotificationPermission
import ch.dreipol.multiplatform.reduxsample.shared.redux.AppState
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.NotificationPermissionDidChangeAction
import ch.dreipol.multiplatform.reduxsample.shared.utils.SettingsHelper
import org.reduxkotlin.Thunk

fun didReceiveNotificationPermissionThunk(rawValue: Int): Thunk<AppState> = { dispatch, getState, _ ->
    val newPermission = NotificationPermission.fromInt(rawValue)
    val state = getState().settingsState.state
    val permission = state?.systemPermission
    if (permission != null && newPermission != permission) {
        SettingsHelper.notificationPermission = newPermission.value
        dispatch(NotificationPermissionDidChangeAction(newPermission))
        dispatch(addOrRemoveNotificationThunk())
    }
}
