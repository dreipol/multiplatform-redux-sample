package ch.dreipol.multiplatform.reduxsample.shared.redux.middleware

import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.ThunkAction
import org.reduxkotlin.middleware

fun <State> convertThunkActionMiddleware() = middleware<State> { store, next, action ->
    val convertedAction = when (action) {
        is ThunkAction -> action.thunk
        else -> action
    }
    next(convertedAction)
}