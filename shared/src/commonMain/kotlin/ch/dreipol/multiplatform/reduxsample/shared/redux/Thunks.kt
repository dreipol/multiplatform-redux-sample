package ch.dreipol.multiplatform.reduxsample.shared.redux

import ch.dreipol.dreimultiplatform.launchAndWait
import ch.dreipol.multiplatform.reduxsample.shared.database.DisposalDataStore
import ch.dreipol.multiplatform.reduxsample.shared.database.DisposalType
import ch.dreipol.multiplatform.reduxsample.shared.network.ServiceFactory
import org.reduxkotlin.Thunk

fun syncDisposalsThunk(): Thunk<AppState> = { dispatch, _, _ ->
    launchAndWait { ServiceFactory.disposalService().syncDisposals(DisposalType.CARTON) }
    dispatch(loadDisposalsThunk())
}

fun loadDisposalsThunk(): Thunk<AppState> = { dispatch, _, _ ->
    dispatch(DisposalsLoaded(DisposalDataStore().getAllDisposals()))
}