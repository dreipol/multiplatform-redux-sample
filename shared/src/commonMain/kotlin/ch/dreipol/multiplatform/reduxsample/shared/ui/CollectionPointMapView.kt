
package ch.dreipol.multiplatform.reduxsample.shared.ui

import ch.dreipol.dreimultiplatform.reduxkotlin.rootDispatch
import ch.dreipol.multiplatform.reduxsample.shared.database.CollectionPointType
import ch.dreipol.multiplatform.reduxsample.shared.delight.CollectionPoint
import ch.dreipol.multiplatform.reduxsample.shared.redux.thunk.initCollectionPointsThunk
import ch.dreipol.multiplatform.reduxsample.shared.utils.Takeoff

data class CollectionPointMapViewState(
    val filter: List<MapFilterItem> = CollectionPointType.values().toList().map { MapFilterItem(it) },
    val collectionPoints: List<CollectionPoint> = emptyList(),
    val selectedCollectionPoint: CollectionPointViewState? = null,
    val loaded: Boolean = false,
) {
    companion object {
        const val INITIAL_LAT = 47.3667
        const val INITIAL_LON = 8.5500
        const val INITIAL_ZOOM = 12
    }
}

data class MapFilterItem(
    val collectionPointType: CollectionPointType,
    val isSelected: Boolean = false,
)

data class CollectionPointViewState(
    val collectionPoint: CollectionPoint,
    val isExpanded: Boolean,
    val title: String,
    val collectionPointTypes: List<CollectionPointType>,
    val wheelChairAccessibleTitle: String?,
    val wheelChairAccessibleIcon: String?,
    val address: String?,
    val navigationLink: Takeoff,
    val phoneNumber: Takeoff,
    val website: Takeoff,
) {

    val arrowIcon: String
        get() {
            return if (isExpanded) "ic_24_chevron_down" else "ic_24_chevron_up"
        }
}

interface CollectionPointMapView : BaseView {
    override fun presenter() = collectionPointMapPresenter

    fun render(collectionPointMapViewState: CollectionPointMapViewState)
}

val collectionPointMapPresenter = presenter<CollectionPointMapView> {
    {
        if (state.collectionPointMapViewState.loaded.not()) {
            rootDispatch(initCollectionPointsThunk())
        }
        select({ it.collectionPointMapViewState }) { render(state.collectionPointMapViewState) }
    }
}