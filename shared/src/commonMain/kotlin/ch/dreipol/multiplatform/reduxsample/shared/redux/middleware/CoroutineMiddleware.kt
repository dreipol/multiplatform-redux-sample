package ch.dreipol.multiplatform.reduxsample.shared.redux.middleware

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.reduxkotlin.Middleware
import org.reduxkotlin.middleware
import kotlin.coroutines.CoroutineContext

/*
 * Middleware that moves rest of the middleware/reducer chain to a coroutine using the given context.
 */
fun <State> coroutineMiddleware(context: CoroutineContext): Middleware<State> {
    val scope = CoroutineScope(context)
    return middleware { _, next, action ->
        scope.launch {
            next(action)
        }
    }
}