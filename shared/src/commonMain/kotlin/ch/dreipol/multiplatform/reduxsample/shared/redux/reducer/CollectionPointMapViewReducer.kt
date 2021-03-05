package ch.dreipol.multiplatform.reduxsample.shared.redux.reducer

import ch.dreipol.multiplatform.reduxsample.shared.database.CollectionPointType
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.CollectionPointsLoadedAction
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.SelectCollectionPointAction
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.DeselectCollectionPointAction
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.UpdateFilterAction
import ch.dreipol.multiplatform.reduxsample.shared.ui.CollectionPointMapViewState
import ch.dreipol.multiplatform.reduxsample.shared.ui.CollectionPointViewState
import ch.dreipol.multiplatform.reduxsample.shared.utils.Takeoff
import org.reduxkotlin.Reducer

private const val WHEEL_CHAIR_ACCESSIBLE = "collection_point_wheelchair_accessible"
private const val WHEEL_CHAIR_ICON = "ic_24_wheelchair"
private const val NAVIGATION_TITLE = "collection_point_navigation_title"

val collectionPointMapViewReducer: Reducer<CollectionPointMapViewState> = { state, action ->
    when (action) {
        is UpdateFilterAction -> state.copy(filter = action.newFilter)
        is CollectionPointsLoadedAction -> state.copy(collectionPoints = action.collectionPoints, loaded = true)
        is DeselectCollectionPointAction -> state.copy(selectedCollectionPoint = null)
        is SelectCollectionPointAction -> {
            val collectionPointViewState = state.collectionPoints.first({ it.id == action.collectionPointId })?.let { selectedPoint ->
                val collectionPointTypes = CollectionPointType.values().filter {
                    when (it) {
                        CollectionPointType.GLASS -> selectedPoint.glass
                        CollectionPointType.OIL -> selectedPoint.oil
                        CollectionPointType.METAL -> selectedPoint.metal
                    }
                }
                val wheelChairTitle = if (selectedPoint.wheelChairAccessible) WHEEL_CHAIR_ACCESSIBLE else null
                val wheelChairAccessibleIcon = if (selectedPoint.wheelChairAccessible) WHEEL_CHAIR_ICON else null
                val navigationLink = "TODO"
                val phoneNumber = selectedPoint.phone
                val website = selectedPoint.website
                CollectionPointViewState(
                    selectedPoint,
                    false,
                    selectedPoint.name,
                    collectionPointTypes,
                    wheelChairTitle,
                    wheelChairAccessibleIcon,
                    selectedPoint.address,
                    Takeoff(NAVIGATION_TITLE, navigationLink),
                    Takeoff(phoneNumber, phoneNumber),
                    Takeoff(website, website)
                )
            }
            state.copy(selectedCollectionPoint = collectionPointViewState)
        }
        else -> state
    }
}