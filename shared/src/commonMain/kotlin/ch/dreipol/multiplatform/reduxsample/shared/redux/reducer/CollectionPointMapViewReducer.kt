package ch.dreipol.multiplatform.reduxsample.shared.redux.reducer

import ch.dreipol.multiplatform.reduxsample.shared.database.CollectionPointType
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.CollectionPointsLoadedAction
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.SelectCollectionPointAction
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.UpdateFilterAction
import ch.dreipol.multiplatform.reduxsample.shared.ui.CollectionPointMapViewState
import ch.dreipol.multiplatform.reduxsample.shared.ui.CollectionPointViewState
import ch.dreipol.multiplatform.reduxsample.shared.utils.Takeoff
import org.reduxkotlin.Reducer

private const val WHEEL_CHAIR_ACCESSIBLE = "collection_point_wheelchair_accessible"
private const val WHEEL_CHAIR_ICON = "ic_24_wheelchair"
private const val NAVIGATION_TITLE = "collektion_point_navigation_title"

val collectionPointMapViewReducer: Reducer<CollectionPointMapViewState> = { state, action ->
    when (action) {
        is UpdateFilterAction -> state.copy(filter = action.newFilter)
        is CollectionPointsLoadedAction -> state.copy(collectionPoints = action.collectionPoints, loaded = true)
        is SelectCollectionPointAction -> {
            val collectionPointTypes = CollectionPointType.values().filter {
                when (it) {
                    CollectionPointType.GLASS -> action.collectionPoint.glass
                    CollectionPointType.OIL -> action.collectionPoint.oil
                    CollectionPointType.METAL -> action.collectionPoint.metal
                }
            }
            val wheelChairTitle = if (action.collectionPoint.wheelChairAccessible) WHEEL_CHAIR_ACCESSIBLE else null
            val wheelChairAccessibleIcon = if (action.collectionPoint.wheelChairAccessible) WHEEL_CHAIR_ICON else null
            val navigationLink = "TODO"
            val phoneNumber = action.collectionPoint.phone
            val website = action.collectionPoint.website
            val collectionPointViewState = CollectionPointViewState(
                false,
                action.collectionPoint.name,
                collectionPointTypes,
                wheelChairTitle,
                wheelChairAccessibleIcon,
                action.collectionPoint.address,
                Takeoff(NAVIGATION_TITLE, navigationLink),
                Takeoff(phoneNumber, phoneNumber),
                Takeoff(website, website)
            )
            state.copy(selectedCollectionPoint = collectionPointViewState)
        }
        else -> state
    }
}