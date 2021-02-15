package ch.dreipol.multiplatform.reduxsample.shared.redux.thunk

import ch.dreipol.dreimultiplatform.reduxkotlin.permissions.NotificationPermission
import ch.dreipol.multiplatform.reduxsample.shared.redux.AppState
import org.reduxkotlin.Thunk

fun didReceiveNotificationPermissionThunk(rawValue: Int): Thunk<AppState> = { dispatch, getState, _ ->
    val newPermission = NotificationPermission.fromInt(rawValue)

    val permission = getState().settingsState.state?.systemPermission
    if (permission != null && newPermission != permission) {
        dispatch(addOrRemoveNotificationThunk())
    }
}