package ch.dreipol.multiplatform.reduxsample.shared.redux

import ch.dreipol.dreimultiplatform.reduxkotlin.presenterEnhancer
import ch.dreipol.dreimultiplatform.uiDispatcher
import ch.dreipol.multiplatform.reduxsample.shared.redux.middleware.coroutineMiddleware
import ch.dreipol.multiplatform.reduxsample.shared.redux.middleware.loggerMiddleware
import ch.dreipol.multiplatform.reduxsample.shared.redux.middleware.onboardingMiddleware
import ch.dreipol.multiplatform.reduxsample.shared.redux.reducer.rootReducer
import ch.dreipol.multiplatform.reduxsample.shared.utils.AppLanguage
import org.reduxkotlin.applyMiddleware
import org.reduxkotlin.compose
import org.reduxkotlin.createThreadSafeStore
import org.reduxkotlin.createThunkMiddleware

class ReduxSampleApp(deviceLanguage: AppLanguage) {
    val store = createThreadSafeStore(
        rootReducer,
        AppState.initialState(deviceLanguage),
        compose(
            listOf(
                presenterEnhancer(uiDispatcher),
                applyMiddleware(
                    coroutineMiddleware(uiDispatcher),
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