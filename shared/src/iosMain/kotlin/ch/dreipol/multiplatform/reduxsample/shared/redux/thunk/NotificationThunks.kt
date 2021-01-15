package ch.dreipol.multiplatform.reduxsample.shared.redux.thunk


import ch.dreipol.multiplatform.reduxsample.shared.redux.AppState
import org.reduxkotlin.Thunk
import platform.Foundation.NSNotificationCenter

val ShouldRequestNotificationAuthorization = "ShouldRequestNotificationAuthorization"

actual fun checkSystemPermissionsThunk(): Thunk<AppState> = { dispatch, _, _ ->
        NSNotificationCenter.defaultCenter.postNotificationName(ShouldRequestNotificationAuthorization, null)

//This should be the way to go, but that ends in an IncorrectDereferenceException
//    val center = UNUserNotificationCenter.currentNotificationCenter()
//    val options = UNNotificationPresentationOptionAlert.plus(UNNotificationPresentationOptionSound)
//    val completion: (Boolean, NSError?) -> kotlin.Unit = { isSuccess, error ->
//        if (isSuccess.not()) {
//            CoroutineScope(uiDispatcher).launch {
//                dispatch(removeNotificationThunk())
//                error?.let { kermit().e { it.localizedDescription } }
//            }
//        }
//    }
//    center.requestAuthorizationWithOptions(options, completion.freeze())
}
