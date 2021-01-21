package ch.dreipol.multiplatform.reduxsample.shared.redux

import ch.dreipol.dreimultiplatform.reduxkotlin.presenterEnhancer
import ch.dreipol.dreimultiplatform.uiDispatcher
import ch.dreipol.multiplatform.reduxsample.shared.redux.middleware.convertThunkActionMiddleware
import ch.dreipol.multiplatform.reduxsample.shared.redux.middleware.coroutineMiddleware
import ch.dreipol.multiplatform.reduxsample.shared.redux.middleware.loggerMiddleware
import ch.dreipol.multiplatform.reduxsample.shared.redux.middleware.onboardingMiddleware
import ch.dreipol.multiplatform.reduxsample.shared.redux.reducer.rootReducer
import org.reduxkotlin.applyMiddleware
import org.reduxkotlin.compose
import org.reduxkotlin.createThreadSafeStore
import org.reduxkotlin.createThunkMiddleware

class ReduxSampleApp() {
    val store = createThreadSafeStore(
        rootReducer,
        AppState.initialState(),
        compose(
            listOf(
                presenterEnhancer(uiDispatcher),
                applyMiddleware(
                    coroutineMiddleware(uiDispatcher),
                    convertThunkActionMiddleware(),
                    loggerMiddleware(),
                    createThunkMiddleware(),
                    onboardingMiddleware(),
                ),
            )
        )
    )

    init {
        store.dispatch(loadSavedSettingsThunk())
        store.dispatch(initialNavigationThunk())
    }
}