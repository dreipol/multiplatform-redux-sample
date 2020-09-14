package ch.dreipol.multiplatform.reduxsample.shared.redux

import ch.dreipol.multiplatform.reduxsample.shared.database.DatabaseHelper
import org.reduxkotlin.Middleware
import org.reduxkotlin.Store
import org.reduxkotlin.middleware

fun loadDataMiddleware(): Middleware<AppState> {
    return middleware { store, next, action ->
        when (action) {
            NavigationActions.DASHBOARD -> loadDisposals(store)
            is DisposalsSynced -> loadDisposals(store)
        }
        next(action)
    }
}

private fun loadDisposals(store: Store<AppState>) {
    store.dispatch(DisposalsChanged(DatabaseHelper.database.disposalQueries.findAll().executeAsList()))
}