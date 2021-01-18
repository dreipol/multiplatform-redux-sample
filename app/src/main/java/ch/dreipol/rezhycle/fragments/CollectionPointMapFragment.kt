package ch.dreipol.rezhycle.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import ch.dreipol.dreimultiplatform.reduxkotlin.PresenterLifecycleObserver
import ch.dreipol.multiplatform.reduxsample.shared.ui.CollectionPointMapView
import ch.dreipol.multiplatform.reduxsample.shared.ui.CollectionPointMapViewState
import ch.dreipol.rezhycle.databinding.FragmentCollectionPointMapBinding
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class CollectionPointMapFragment : BaseFragment<FragmentCollectionPointMapBinding, CollectionPointMapView>(), CollectionPointMapView {
    override val presenterObserver = PresenterLifecycleObserver(this)

    private lateinit var mapView: MapView

    override fun createBinding(): FragmentCollectionPointMapBinding {
        return FragmentCollectionPointMapBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mapView = viewBinding.map
        mapView.onCreate(savedInstanceState)
        mapView.onResume()
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun render(collectionPointMapViewState: CollectionPointMapViewState) {
        mapView.getMapAsync {
            val sydney = LatLng(-33.852, 151.211)
            it.addMarker(
                MarkerOptions()
                    .position(sydney)
                    .title("Marker in Sydney")
            )
        }
    }
}