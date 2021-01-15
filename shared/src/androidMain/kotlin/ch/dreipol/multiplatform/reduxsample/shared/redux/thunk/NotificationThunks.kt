package ch.dreipol.multiplatform.reduxsample.shared.redux.thunk

import ch.dreipol.multiplatform.reduxsample.shared.redux.AppState
import org.reduxkotlin.Thunk

actual fun checkSystemPermissionsThunk(): Thunk<AppState> = { _, _, _ ->
    // Nothing to do
}