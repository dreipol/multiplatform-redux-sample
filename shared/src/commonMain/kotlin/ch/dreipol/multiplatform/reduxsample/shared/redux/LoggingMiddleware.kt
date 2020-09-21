package ch.dreipol.multiplatform.reduxsample.shared.redux

import ch.dreipol.dreimultiplatform.kermit
import org.reduxkotlin.middleware

val loggerMiddleware = middleware<AppState> { _, next, action ->
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