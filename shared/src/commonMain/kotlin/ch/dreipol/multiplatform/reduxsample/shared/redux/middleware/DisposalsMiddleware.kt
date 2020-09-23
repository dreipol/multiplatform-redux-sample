package ch.dreipol.multiplatform.reduxsample.shared.redux.middleware

import ch.dreipol.multiplatform.reduxsample.shared.redux.AppState
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.DisposalsLoadedAction
import ch.dreipol.multiplatform.reduxsample.shared.redux.loadDisposalsThunk
import ch.dreipol.multiplatform.reduxsample.shared.redux.navigation.NavigationAction
import ch.dreipol.multiplatform.reduxsample.shared.redux.syncDisposalsThunk
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