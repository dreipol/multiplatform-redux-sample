package ch.dreipol.multiplatform.reduxsample.shared.redux

import ch.dreipol.multiplatform.reduxsample.shared.redux.navigation.NavigationAction
import org.reduxkotlin.middleware

fun disposalsMiddleware() = middleware<AppState> { store, next, action ->
    when (action) {
        NavigationAction.DASHBOARD -> {
            store.dispatch(loadDisposalsThunk())
        }
        is DisposalsLoadedAction -> {
            if (store.state.dashboardViewState.disposalsState.loaded.not()) {
                store.dispatch(syncDisposalsThunk())
            }
        }
    }
    next(action)
}