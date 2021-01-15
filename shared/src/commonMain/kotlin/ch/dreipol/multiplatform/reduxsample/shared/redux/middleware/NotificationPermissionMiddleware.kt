package ch.dreipol.multiplatform.reduxsample.shared.redux.middleware

import ch.dreipol.multiplatform.reduxsample.shared.redux.AppState
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.NavigationAction
import org.reduxkotlin.middleware

fun notificationPermissionMiddleware() = middleware<AppState> { store, next, action ->
    when (action) {
        NavigationAction.ONBOARDING_NEXT -> print("check permission")
    }
    next(action)
}