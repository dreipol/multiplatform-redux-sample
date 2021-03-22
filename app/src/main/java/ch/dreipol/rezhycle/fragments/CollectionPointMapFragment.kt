package ch.dreipol.rezhycle.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat.checkSelfPermission
import ch.dreipol.dreimultiplatform.reduxkotlin.PresenterLifecycleObserver
import ch.dreipol.dreimultiplatform.reduxkotlin.rootDispatch
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.DeselectCollectionPointAction
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.SelectCollectionPointAction
import ch.dreipol.multiplatform.reduxsample.shared.ui.CollectionPointMapView
import ch.dreipol.multiplatform.reduxsample.shared.ui.CollectionPointMapViewState
import ch.dreipol.multiplatform.reduxsample.shared.utils.MapChangeSet
import ch.dreipol.multiplatform.reduxsample.shared.utils.MapIconLayer
import ch.dreipol.multiplatform.reduxsample.shared.utils.PinKind
import ch.dreipol.rezhycle.R
import ch.dreipol.rezhycle.databinding.FragmentCollectionPointMapBinding
import ch.dreipol.rezhycle.utils.getBitmapFromVectorDrawable
import ch.dreipol.rezhycle.utils.getDrawableIdentifier
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.*

class CollectionPointMapFragment :
    BaseFragment<FragmentCollectionPointMapBinding, CollectionPointMapView>(),
    CollectionPointMapView,
    GoogleMap.OnMarkerClickListener,
    MapIconLayer {

    companion object {
        const val LOCATION_PERMISSION_REQUEST = 1000
    }

    override val presenterObserver = PresenterLifecycleObserver(this)
    private val markers = mutableListOf<Marker>()
    private var selectedMarker: Marker? = null
    private lateinit var mapView: MapView
    private val unselectedIcon: Bitmap? by lazy {
        requireContext().getBitmapFromVectorDrawable(requireContext().getDrawableIdentifier(PinKind.UNSELECTED.icon))
    }
    private val selectedIcon: Bitmap? by lazy {
        requireContext().getBitmapFromVectorDrawable(requireContext().getDrawableIdentifier(PinKind.SELECTED.icon))
    }

    override val iconIds: Set<String>
        get() = markers.map { it.tag }.filterIsInstance<String>().toSet()

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
            it.setOnMapClickListener { rootDispatch(DeselectCollectionPointAction(null)) }
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
        val changeSet = MapChangeSet(this, collectionPointMapViewState.collectionPoints, PinKind.UNSELECTED)
        changeSet.updateLayer()
        selectedMarker?.let {
            it.setIcon(BitmapDescriptorFactory.fromBitmap(unselectedIcon))
            it.zIndex = 1f
        }
        collectionPointMapViewState.selectedCollectionPoint?.let { selectedCollectionPoint ->
            val marker = markers.first { it.tag == selectedCollectionPoint.collectionPoint.id }
            marker.setIcon(BitmapDescriptorFactory.fromBitmap(selectedIcon))
            marker.zIndex = 2f
            selectedMarker = marker
            mapView.getMapAsync { it.animateCamera(CameraUpdateFactory.newLatLng(marker.position)) }
        } ?: run { selectedMarker = null }
    }

    override fun onMarkerClick(p0: Marker?): Boolean {
        (p0?.tag as? String)?.let {
            if (p0 == selectedMarker) {
                rootDispatch(DeselectCollectionPointAction(it))
            } else {
                rootDispatch(SelectCollectionPointAction(it))
            }
        }
        return true
    }

    override fun removeIcons(toRemove: Set<String>) {
        toRemove.map { markers.find { it.tag == it } }.forEach {
            it?.remove()
            markers.remove(it)
        }
    }

    override fun addIcon(id: String, lat: Double, lon: Double, pinKind: PinKind) {
        val latLng = LatLng(lat, lon)
        val bitmap = if (pinKind == PinKind.SELECTED) selectedIcon else unselectedIcon
        mapView.getMapAsync { map ->
            val marker = map.addMarker(
                MarkerOptions()
                    .position(latLng)
                    .icon(BitmapDescriptorFactory.fromBitmap(bitmap))
            )
            marker.tag = id
            markers.add(marker)
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
}