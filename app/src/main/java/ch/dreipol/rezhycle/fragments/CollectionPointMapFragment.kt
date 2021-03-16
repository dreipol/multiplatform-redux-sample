package ch.dreipol.rezhycle.fragments

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import ch.dreipol.dreimultiplatform.reduxkotlin.PresenterLifecycleObserver
import ch.dreipol.dreimultiplatform.reduxkotlin.rootDispatch
import ch.dreipol.multiplatform.reduxsample.shared.delight.CollectionPoint
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.SelectCollectionPointAction
import ch.dreipol.multiplatform.reduxsample.shared.ui.CollectionPointMapView
import ch.dreipol.multiplatform.reduxsample.shared.ui.CollectionPointMapViewState
import ch.dreipol.rezhycle.R
import ch.dreipol.rezhycle.databinding.FragmentCollectionPointMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.*

class CollectionPointMapFragment :
    BaseFragment<FragmentCollectionPointMapBinding, CollectionPointMapView>(),
    CollectionPointMapView,
    GoogleMap.OnMarkerClickListener {

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
            it.setOnMarkerClickListener(this)
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
        mapView.getMapAsync { map ->
            map.clear()
            val selectedPointName = collectionPointMapViewState.selectedCollectionPoint?.title
            var selectedPointMarker: CollectionPoint? = null
            collectionPointMapViewState.collectionPoints.forEach {
                val latLng = LatLng(it.lat, it.lon)
                val markerIcon = if (it.name.equals(selectedPointName)) {
                    selectedPointMarker = it
                    R.drawable.ic_24_electro_colored // TODO use correct icon
                } else {
                    R.drawable.ic_24_location
                }
                val marker = map.addMarker(
                    MarkerOptions()
                        .position(latLng)
                        .title(it.name)
                        .icon(
                            BitmapDescriptorFactory.fromBitmap(
                                context?.let { it1 -> getBitmapFromVectorDrawable(it1, markerIcon) }
                            )
                        )
                )
                marker.tag = it.id
            }

            selectedPointMarker?.let {
                map.animateCamera(CameraUpdateFactory.newLatLng(LatLng(it.lat, it.lon)))
            }
        }
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

    fun getBitmapFromVectorDrawable(context: Context, drawableId: Int): Bitmap? {
        val drawable = ContextCompat.getDrawable(context, drawableId)
        drawable?.let {
            val bitmap = Bitmap.createBitmap(
                it.intrinsicWidth,
                it.intrinsicHeight, Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            it.setBounds(0, 0, canvas.width, canvas.height)
            it.draw(canvas)
            return bitmap
        }

        return null
    }

    override fun onMarkerClick(p0: Marker?): Boolean {
        // Retrieve the data from the marker.
        if (p0?.tag is CollectionPoint) {
            rootDispatch(SelectCollectionPointAction(p0.tag as String))
        }

        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false
    }
}