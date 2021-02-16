package ch.dreipol.rezhycle.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat.checkSelfPermission
import ch.dreipol.dreimultiplatform.reduxkotlin.PresenterLifecycleObserver
import ch.dreipol.multiplatform.reduxsample.shared.ui.CollectionPointMapView
import ch.dreipol.multiplatform.reduxsample.shared.ui.CollectionPointMapViewState
import ch.dreipol.rezhycle.R
import ch.dreipol.rezhycle.databinding.FragmentCollectionPointMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions

class CollectionPointMapFragment : BaseFragment<FragmentCollectionPointMapBinding, CollectionPointMapView>(), CollectionPointMapView {

    companion object {
        const val LOCATION_PERMISSION_REQUEST = 1000
    }

    override val presenterObserver = PresenterLifecycleObserver(this)
    private lateinit var mapView: MapView

    override fun createBinding(): FragmentCollectionPointMapBinding {
        return FragmentCollectionPointMapBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mapView = viewBinding.map
        mapView.onCreate(savedInstanceState)
        mapView.onResume()
        mapView.getMapAsync {
            it.setMapStyle(MapStyleOptions.loadRawResourceStyle(requireContext(), R.raw.style_json))
            it.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(CollectionPointMapViewState.INITIAL_LAT, CollectionPointMapViewState.INITIAL_LON),
                    CollectionPointMapViewState.INITIAL_ZOOM.toFloat()
                )
            )
            // TODO
            /*if (hasLocationPermission()) {
                it.isMyLocationEnabled = true
            } else {
                requestPermissions(
                    listOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION).toTypedArray(),
                    LOCATION_PERMISSION_REQUEST
                )
            }*/
        }
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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == LOCATION_PERMISSION_REQUEST && grantResults.firstOrNull() == PackageManager.PERMISSION_GRANTED) {
            mapView.getMapAsync {
                it.isMyLocationEnabled = hasLocationPermission()
            }
        }
    }

    override fun render(collectionPointMapViewState: CollectionPointMapViewState) {
        // TODO
        /*mapView.getMapAsync { map ->
            map.clear()
            collectionPointMapViewState.collectionPoints.forEach {
                val latLng = LatLng(it.lat, it.lon)
                map.addMarker(MarkerOptions().position(latLng).title(it.name))
            }
        }*/
    }

    private fun hasLocationPermission(): Boolean {
        return checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED ||
            checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
    }
}