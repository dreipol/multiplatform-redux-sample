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
import ch.admin.geo.openswissmaps.shared.layers.config.SwisstopoLayerType
import ch.admin.geo.openswissmaps.view.SwisstopoMapView
import ch.dreipol.dreimultiplatform.reduxkotlin.PresenterLifecycleObserver
import ch.dreipol.multiplatform.reduxsample.shared.ui.CollectionPointMapView
import ch.dreipol.multiplatform.reduxsample.shared.ui.CollectionPointMapViewState
import ch.dreipol.rezhycle.databinding.FragmentCollectionPointMapBinding
import ch.dreipol.rezhycle.utils.MapIconLayer
import io.openmobilemaps.mapscore.shared.map.coordinates.Coord
import io.openmobilemaps.mapscore.shared.map.coordinates.CoordinateSystemIdentifiers
import io.openmobilemaps.mapscore.shared.map.layers.icon.IconInfoInterface
import io.openmobilemaps.mapscore.shared.map.layers.icon.IconLayerCallbackInterface
import io.openmobilemaps.mapscore.shared.map.layers.icon.IconLayerInterface

class CollectionPointMapFragment :
    BaseFragment<FragmentCollectionPointMapBinding, CollectionPointMapView>(),
    CollectionPointMapView {

    companion object {
        const val LOCATION_PERMISSION_REQUEST = 1000
    }

    override val presenterObserver = PresenterLifecycleObserver(this)

    private lateinit var mapView: SwisstopoMapView
    private lateinit var unselectedLayer: MapIconLayer
    private lateinit var selectedLayer: MapIconLayer

    override fun createBinding(): FragmentCollectionPointMapBinding {
        return FragmentCollectionPointMapBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mapView = viewBinding.map
        mapView.registerLifecycle(lifecycle)
        mapView.getCamera()
            .moveToCenterPositionZoom(
                Coord(
                    CoordinateSystemIdentifiers.EPSG4326(), CollectionPointMapViewState.INITIAL_LON,
                    CollectionPointMapViewState.INITIAL_LAT, 0.0
                ),
                CollectionPointMapViewState.INITIAL_ZOOM * 10000.0,
                true
            )
        mapView.setBaseLayerType(SwisstopoLayerType.PIXELKARTE_GRAUSTUFEN)
        unselectedLayer = MapIconLayer(IconLayerInterface.create())
        unselectedLayer.iconLayer.setCallbackHandler(object : IconLayerCallbackInterface() {
            override fun onClickConfirmed(icons: ArrayList<IconInfoInterface>): Boolean {
                icons.firstOrNull()?.let { onMarkerClick(it) }
                return true
            }

        })
        mapView.addLayer(unselectedLayer.iconLayer.asLayerInterface())

        selectedLayer = MapIconLayer(IconLayerInterface.create())
        mapView.addLayer(selectedLayer.iconLayer.asLayerInterface())
        super.onViewCreated(view, savedInstanceState)
    }

    override fun render(collectionPointMapViewState: CollectionPointMapViewState) {
        /*mapView.getMapAsync { map ->
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

    fun onMarkerClick(marker: IconInfoInterface) {
        /*// Retrieve the data from the marker.
        if (p0?.tag is CollectionPoint) {
            rootDispatch(SelectCollectionPointAction(p0.tag as String))
        }

        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false*/
    }
}