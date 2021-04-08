package ch.dreipol.multiplatform.reduxsample.shared.redux.middleware

import ch.dreipol.dreimultiplatform.kermit
import ch.dreipol.multiplatform.reduxsample.shared.redux.AppState
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.PrintableAction
import org.reduxkotlin.middleware

fun loggerMiddleware() = middleware<AppState> { _, next, action ->
    val actionLogName =
        when (action) {
            is Function<*> -> "Thunk Function"
            is PrintableAction -> action.toString()
            else -> action::class.simpleName
        }
    kermit().d {
        "\n********************************************\n" +
            "******** DISPATCHED action: $actionLogName\n" +
            "********************************************"
    }
    next(action)
}