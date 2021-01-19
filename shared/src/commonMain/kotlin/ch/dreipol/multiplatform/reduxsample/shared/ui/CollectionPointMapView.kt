package ch.dreipol.multiplatform.reduxsample.shared.ui

data class CollectionPointMapViewState(
    val collectionPoints: List<Unit> = emptyList(),
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
        select({ it.collectionPointMapViewState }) { render(state.collectionPointMapViewState) }
    }
}