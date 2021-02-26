package ch.dreipol.multiplatform.reduxsample.shared.redux.reducer

import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.CollectionPointsLoadedAction
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.UpdateFilterAction
import ch.dreipol.multiplatform.reduxsample.shared.ui.CollectionPointMapViewState
import org.reduxkotlin.Reducer

val collectionPointMapViewReducer: Reducer<CollectionPointMapViewState> = { state, action ->
    when (action) {
        is UpdateFilterAction -> state.copy(filter = action.newFilter)
        is CollectionPointsLoadedAction -> state.copy(collectionPoints = action.collectionPoints, loaded = true)
        else -> state
    }
}