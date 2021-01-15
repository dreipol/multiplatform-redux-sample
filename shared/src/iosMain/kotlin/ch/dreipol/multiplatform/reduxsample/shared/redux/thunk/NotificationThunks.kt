package ch.dreipol.multiplatform.reduxsample.shared.redux.thunk

import ch.dreipol.dreimultiplatform.kermit
import ch.dreipol.multiplatform.reduxsample.shared.redux.AppState
import org.reduxkotlin.Thunk
import platform.UserNotifications.UNNotificationPresentationOptionAlert
import platform.UserNotifications.UNNotificationPresentationOptionSound
import platform.UserNotifications.UNUserNotificationCenter

@ExperimentalUnsignedTypes
actual fun checkSystemPermissionsThunk(): Thunk<AppState> = { dispatch, _, _ ->
    val center = UNUserNotificationCenter.currentNotificationCenter()
    center.requestAuthorizationWithOptions(
        (UNNotificationPresentationOptionAlert + UNNotificationPresentationOptionSound)
    ) { isSuccess, error ->
        if (isSuccess.not()) {
            dispatch(removeNotificationThunk())
            error?.let { kermit().e { it.localizedDescription } }
        }
    }
}