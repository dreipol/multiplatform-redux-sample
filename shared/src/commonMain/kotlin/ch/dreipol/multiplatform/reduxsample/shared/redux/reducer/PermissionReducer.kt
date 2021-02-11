package ch.dreipol.multiplatform.reduxsample.shared.redux.reducer

import ch.dreipol.multiplatform.reduxsample.shared.redux.PermissionsState
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.NotificationPermissionDidChangeAction
import org.reduxkotlin.Reducer

val permissionsReducer: Reducer<PermissionsState> = { state, action ->
    when (action) {
        is NotificationPermissionDidChangeAction -> {
            if (action.value != state.notificationPermission) {
                PermissionsState(action.value)
            } else {
                state
            }
        }
        else -> state
    }
}
