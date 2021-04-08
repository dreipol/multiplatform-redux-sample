package ch.dreipol.multiplatform.reduxsample.shared.redux.reducer

import ch.dreipol.multiplatform.reduxsample.shared.database.CollectionPointType
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.CollectionPointsLoadedAction
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.DeselectCollectionPointAction
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.SelectCollectionPointAction
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.UpdateFilterAction
import ch.dreipol.multiplatform.reduxsample.shared.ui.CollectionPointMapViewState
import ch.dreipol.multiplatform.reduxsample.shared.ui.CollectionPointViewState
import ch.dreipol.multiplatform.reduxsample.shared.utils.Takeoff
import org.reduxkotlin.Reducer

private const val NAVIGATION_TITLE = "collection_point_navigation_title"

val collectionPointMapViewReducer: Reducer<CollectionPointMapViewState> = { state, action ->
    when (action) {
        is UpdateFilterAction -> state.copy(filter = action.newFilter)
        is CollectionPointsLoadedAction -> state.copy(collectionPoints = action.collectionPoints, loaded = true)
        is DeselectCollectionPointAction -> {
            if (action.collectionPointId == null ||
                action.collectionPointId == state.selectedCollectionPoint?.collectionPoint?.id
            ) {
                state.copy(selectedCollectionPoint = null)
            } else {
                state
            }
        }
        is SelectCollectionPointAction -> {
            val collectionPointViewState = state.collectionPoints.first({ it.id == action.collectionPointId })?.let { selectedPoint ->
                val collectionPointTypes = CollectionPointType.values().filter {
                    when (it) {
                        CollectionPointType.GLASS -> selectedPoint.glass
                        CollectionPointType.OIL -> selectedPoint.oil
                        CollectionPointType.METAL -> selectedPoint.metal
                    }
                }
                val navigationAddress = selectedPoint.address.replace("\n", ", ") + ", CH"
                val phoneNumber = selectedPoint.phone
                val website = selectedPoint.website
                CollectionPointViewState(
                    selectedPoint,
                    false,
                    selectedPoint.name,
                    collectionPointTypes,
                    selectedPoint.wheelChairAccessible,
                    selectedPoint.address,
                    Takeoff(NAVIGATION_TITLE, navigationAddress),
                    Takeoff(phoneNumber, phoneNumber),
                    Takeoff(website, website)
                )
            }
            state.copy(selectedCollectionPoint = collectionPointViewState)
        }
        else -> state
    }
}