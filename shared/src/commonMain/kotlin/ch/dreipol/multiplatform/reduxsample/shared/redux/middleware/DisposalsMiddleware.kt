package ch.dreipol.multiplatform.reduxsample.shared.redux.middleware

import ch.dreipol.multiplatform.reduxsample.shared.redux.AppState
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.DisposalsLoadedAction
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.SettingsLoadedAction
import ch.dreipol.multiplatform.reduxsample.shared.redux.loadDisposalsThunk
import ch.dreipol.multiplatform.reduxsample.shared.redux.syncDisposalsThunk
import org.reduxkotlin.middleware

fun syncDisposalsMiddleware() = middleware<AppState> { store, next, action ->
    when (action) {
        is DisposalsLoadedAction -> {
            if (store.state.dashboardViewState.disposalsState.loaded.not()) {
                store.dispatch(syncDisposalsThunk())
            }
            next(action)
        }
        is SettingsLoadedAction -> {
            next(action)
            store.dispatch(loadDisposalsThunk())
        }
        else -> next(action)
    }
}