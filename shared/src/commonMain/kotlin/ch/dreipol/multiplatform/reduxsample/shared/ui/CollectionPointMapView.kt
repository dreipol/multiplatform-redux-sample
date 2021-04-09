package ch.dreipol.multiplatform.reduxsample.shared.ui

import ch.dreipol.dreimultiplatform.Localize
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

    private fun collectionPointMatchesFilter(collectionPoint: CollectionPoint, filter: List<MapFilterItem>): Boolean = filter.all {
        !it.isSelected || when (it.collectionPointType) {
            CollectionPointType.GLASS -> collectionPoint.glass
            CollectionPointType.METAL -> collectionPoint.metal
            CollectionPointType.OIL -> collectionPoint.oil
        }
    }

    val filteredCollectionPoints: List<CollectionPoint> = collectionPoints.filter { collectionPointMatchesFilter(it, filter) }
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
    val wheelChairAccessible: Boolean,
    val address: String,
    val navigationLink: Takeoff,
    val phoneNumber: Takeoff,
    val website: Takeoff,
) {

    val arrowIcon: String
        get() {
            return if (isExpanded) "ic_24_chevron_down" else "ic_24_chevron_up"
        }

    fun collectionPointTypeTitle(localize: Localize): String =
        collectionPointTypes.joinToString(separator = ", ") { localize.localize(it.translationKey) }

    val wheelChairAccessibleTitle = WHEEL_CHAIR_ACCESSIBLE
    val wheelChairAccessibleIcon = WHEEL_CHAIR_ICON
    val wheelChairTransparency: Float
        get() = if (wheelChairAccessible) 1f else 0.25f

    companion object {
        const val WHEEL_CHAIR_ACCESSIBLE = "collection_point_wheelchair_accessible"
        const val WHEEL_CHAIR_ICON = "ic_24_wheelchair"
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