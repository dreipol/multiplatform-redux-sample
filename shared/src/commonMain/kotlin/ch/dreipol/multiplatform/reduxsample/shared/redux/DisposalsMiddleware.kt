package ch.dreipol.multiplatform.reduxsample.shared.redux

import org.reduxkotlin.middleware

fun disposalsMiddleware() = middleware<AppState> { store, next, action ->
    when (action) {
        NavigationActions.DASHBOARD -> {
            store.dispatch(loadDisposalsThunk())
        }
        is DisposalsLoaded -> {
            if (store.state.dashboardViewState.disposalsState.loaded.not()) {
                store.dispatch(syncDisposalsThunk())
            }
        }
    }
    next(action)
}