package ch.dreipol.multiplatform.reduxsample.shared.redux

import ch.dreipol.dreimultiplatform.reduxkotlin.presenterEnhancer
import ch.dreipol.dreimultiplatform.uiDispatcher
import ch.dreipol.multiplatform.reduxsample.shared.redux.middleware.*
import ch.dreipol.multiplatform.reduxsample.shared.redux.reducer.rootReducer
import ch.dreipol.multiplatform.reduxsample.shared.redux.thunk.initialNavigationThunk
import ch.dreipol.multiplatform.reduxsample.shared.redux.thunk.loadSavedSettingsThunk
import ch.dreipol.multiplatform.reduxsample.shared.utils.AppLanguage
import ch.dreipol.multiplatform.reduxsample.shared.utils.fromLocale
import org.reduxkotlin.applyMiddleware
import org.reduxkotlin.compose
import org.reduxkotlin.createThreadSafeStore
import org.reduxkotlin.createThunkMiddleware

class ReduxSampleApp() {
    val store = createThreadSafeStore(
        rootReducer,
        AppState.initialState(AppLanguage.fromLocale()),
        compose(
            listOf(
                presenterEnhancer(uiDispatcher),
                applyMiddleware(
                    coroutineMiddleware(uiDispatcher),
                    convertThunkActionMiddleware(),
                    loggerMiddleware(),
                    notificationPermissionMiddleware(),
                    createThunkMiddleware(),
                    onboardingMiddleware(),
                    storeRatingMiddleware(),
                ),
            )
        )
    )

    init {
        store.dispatch(loadSavedSettingsThunk())
        store.dispatch(initialNavigationThunk())
    }
}