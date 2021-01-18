package ch.dreipol.multiplatform.reduxsample.shared.ui

data class CollectionPointMapViewState(
    val collectionPoints: List<Unit> = emptyList()
)

interface CollectionPointMapView : BaseView {
    override fun presenter() = collectionPointMapPresenter

    fun render(collectionPointMapViewState: CollectionPointMapViewState)
}

val collectionPointMapPresenter = presenter<CollectionPointMapView> {
    {
        select({ it.collectionPointMapViewState }) { render(state.collectionPointMapViewState) }
    }
}