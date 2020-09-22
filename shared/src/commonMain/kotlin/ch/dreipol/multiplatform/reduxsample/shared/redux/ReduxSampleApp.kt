package ch.dreipol.multiplatform.reduxsample.shared.redux

import ch.dreipol.dreimultiplatform.defaultDispatcher
import ch.dreipol.dreimultiplatform.reduxkotlin.presenterEnhancer
import ch.dreipol.dreimultiplatform.uiDispatcher
import ch.dreipol.multiplatform.reduxsample.shared.redux.middleware.coroutineMiddleware
import ch.dreipol.multiplatform.reduxsample.shared.redux.middleware.disposalsMiddleware
import ch.dreipol.multiplatform.reduxsample.shared.redux.middleware.loggerMiddleware
import ch.dreipol.multiplatform.reduxsample.shared.redux.middleware.onboardingMiddleware
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.reduxkotlin.*
import kotlin.coroutines.CoroutineContext



class ReduxSampleApp {
    val store = createThreadSafeStore(
        rootReducer,
        AppState.INITIAL_STATE,
        compose(
            listOf(
                presenterEnhancer(uiDispatcher),
                applyMiddleware(
                    coroutineMiddleware(defaultDispatcher),
                    loggerMiddleware(),
                    createThunkMiddleware(),
                    disposalsMiddleware(),
                    onboardingMiddleware(),
                ),
            )
        )
    )
}