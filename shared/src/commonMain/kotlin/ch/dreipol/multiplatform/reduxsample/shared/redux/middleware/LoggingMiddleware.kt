package ch.dreipol.multiplatform.reduxsample.shared.redux.middleware

import ch.dreipol.dreimultiplatform.kermit
import ch.dreipol.multiplatform.reduxsample.shared.redux.AppState
import org.reduxkotlin.middleware

fun loggerMiddleware() = middleware<AppState> { _, next, action ->
    val actionLogName =
        when (action) {
            is Function<*> -> "Thunk Function"
            else -> action::class.simpleName
        }
    kermit().d {
        "\n********************************************\n" +
            "******** DISPATCHED action: $actionLogName\n" +
            "********************************************"
    }
    next(action)
}