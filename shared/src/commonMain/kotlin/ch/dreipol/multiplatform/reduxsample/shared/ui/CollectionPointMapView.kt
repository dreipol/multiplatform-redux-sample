package ch.dreipol.multiplatform.reduxsample.shared.ui

import ch.dreipol.dreimultiplatform.reduxkotlin.rootDispatch
import ch.dreipol.multiplatform.reduxsample.shared.delight.CollectionPoint
import ch.dreipol.multiplatform.reduxsample.shared.redux.thunk.loadCollectionPointsThunk

data class CollectionPointMapViewState(
    val collectionPoints: List<CollectionPoint> = emptyList(),
    val loaded: Boolean = false,
) {
    companion object {
        const val INITIAL_LAT = 47.3667
        const val INITIAL_LON = 8.5500
        const val INITIAL_ZOOM = 12
    }
}

interface CollectionPointMapView : BaseView {
    override fun presenter() = collectionPointMapPresenter

    fun render(collectionPointMapViewState: CollectionPointMapViewState)
}

val collectionPointMapPresenter = presenter<CollectionPointMapView> {
    {
        if (state.collectionPointMapViewState.loaded.not()) {
            rootDispatch(loadCollectionPointsThunk())
        }
        select({ it.collectionPointMapViewState }) { render(state.collectionPointMapViewState) }
    }
}