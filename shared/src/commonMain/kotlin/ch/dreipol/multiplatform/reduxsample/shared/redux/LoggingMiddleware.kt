package ch.dreipol.multiplatform.reduxsample.shared.redux

import ch.dreipol.dreimultiplatform.kermit
import org.reduxkotlin.middleware

val loggerMiddleware = middleware<AppState> { _, next, action ->
    kermit().d {
        "\n********************************************\n" +
            "* DISPATCHED action: $action\n" +
            "********************************************"
    }
    next(action)
}