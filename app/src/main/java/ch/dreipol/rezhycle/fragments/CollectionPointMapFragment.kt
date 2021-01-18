package ch.dreipol.rezhycle.fragments

import ch.dreipol.dreimultiplatform.reduxkotlin.PresenterLifecycleObserver
import ch.dreipol.multiplatform.reduxsample.shared.ui.CollectionPointMapView
import ch.dreipol.multiplatform.reduxsample.shared.ui.CollectionPointMapViewState
import ch.dreipol.rezhycle.databinding.FragmentCollectionPointMapBinding

class CollectionPointMapFragment : BaseFragment<FragmentCollectionPointMapBinding, CollectionPointMapView>(), CollectionPointMapView {
    override val presenterObserver = PresenterLifecycleObserver(this)

    override fun createBinding(): FragmentCollectionPointMapBinding {
        return FragmentCollectionPointMapBinding.inflate(layoutInflater)
    }

    override fun render(collectionPointMapViewState: CollectionPointMapViewState) {
        TODO("Not yet implemented")
    }
}