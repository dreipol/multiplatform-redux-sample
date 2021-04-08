package ch.dreipol.rezhycle.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.view.setPadding
import ch.dreipol.dreimultiplatform.Localizer
import ch.dreipol.dreimultiplatform.getString
import ch.dreipol.dreimultiplatform.reduxkotlin.PresenterLifecycleObserver
import ch.dreipol.dreimultiplatform.reduxkotlin.rootDispatch
import ch.dreipol.multiplatform.reduxsample.shared.database.CollectionPointType
import ch.dreipol.multiplatform.reduxsample.shared.delight.CollectionPoint
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.DeselectCollectionPointAction
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.SelectCollectionPointAction
import ch.dreipol.multiplatform.reduxsample.shared.ui.CollectionPointMapView
import ch.dreipol.multiplatform.reduxsample.shared.ui.CollectionPointMapViewState
import ch.dreipol.multiplatform.reduxsample.shared.ui.CollectionPointViewState
import ch.dreipol.multiplatform.reduxsample.shared.utils.MapChangeSet
import ch.dreipol.multiplatform.reduxsample.shared.utils.MapPinLayer
import ch.dreipol.multiplatform.reduxsample.shared.utils.PinKind
import ch.dreipol.rezhycle.R
import ch.dreipol.rezhycle.databinding.FragmentCollectionPointMapBinding
import ch.dreipol.rezhycle.databinding.ViewCollectionPointBinding
import ch.dreipol.rezhycle.utils.GoogleMapsUrlBuilder
import ch.dreipol.rezhycle.utils.getBitmapFromVectorDrawable
import ch.dreipol.rezhycle.utils.getDrawableIdentifier
import com.github.dreipol.dreidroid.utils.ViewUtils
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.*

private val cameraPadding = 64 // offset from edges of the map in pixels

class CollectionPointMapFragment :
    BaseFragment<FragmentCollectionPointMapBinding, CollectionPointMapView>(),
    CollectionPointMapView,
    GoogleMap.OnMarkerClickListener,
    MapPinLayer,
    MotionLayout.TransitionListener {

    override val presenterObserver = PresenterLifecycleObserver(this)
    private val markers = mutableListOf<Marker>()
    private var selectedMarker: Marker? = null
    private lateinit var mapView: MapView
    private lateinit var map: GoogleMap
    private val unselectedIcon: BitmapDescriptor? by lazy {
        BitmapDescriptorFactory.fromBitmap(
            requireContext().getBitmapFromVectorDrawable(requireContext().getDrawableIdentifier(PinKind.UNSELECTED.icon))
        )
    }
    private val selectedIcon: BitmapDescriptor? by lazy {
        BitmapDescriptorFactory.fromBitmap(
            requireContext().getBitmapFromVectorDrawable(requireContext().getDrawableIdentifier(PinKind.SELECTED.icon))
        )
    }

    override val pinIds: Set<String>
        get() = markers.map { it.tag }.filterIsInstance<String>().toSet()

    override fun createBinding(): FragmentCollectionPointMapBinding {
        return FragmentCollectionPointMapBinding.inflate(layoutInflater)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        mapView = viewBinding.map
        mapView.onCreate(savedInstanceState)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewBinding.collectionPointViewMotion.addTransitionListener(this)
        val navigationLinkView = viewBinding.collectionPointView.navigationLink
        ViewUtils.useTouchDownListener(navigationLinkView, navigationLinkView)
        mapView.getMapAsync {
            map = it
            it.setMapStyle(MapStyleOptions.loadRawResourceStyle(requireContext(), R.raw.style_json))
            it.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(CollectionPointMapViewState.INITIAL_LAT, CollectionPointMapViewState.INITIAL_LON),
                    CollectionPointMapViewState.INITIAL_ZOOM.toFloat()
                )
            )
            it.setOnMarkerClickListener(this)
            it.setOnMapClickListener { rootDispatch(DeselectCollectionPointAction(null)) }
            super.onViewCreated(view, savedInstanceState)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding.collectionPointViewMotion.removeTransitionListener(this)
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        mapView.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        mapView.onDestroy()
        super.onDestroy()
    }

    override fun onLowMemory() {
        mapView.onLowMemory()
        super.onLowMemory()
    }

    private fun zoomToAllPins() {
        val bounds = getMarkerBounds()
        showBounds(bounds)
    }

    private fun getMarkerBounds(): LatLngBounds? {
        val builder = LatLngBounds.Builder()
        for (marker in markers) {
            builder.include(marker.getPosition())
        }
        val bounds = builder.build()
        return bounds
    }

    private fun showBounds(bounds: LatLngBounds?) {
        val cu = CameraUpdateFactory.newLatLngBounds(bounds, cameraPadding)
        mapView.getMapAsync { map ->
            map.animateCamera(cu)
        }
    }

    override fun render(collectionPointMapViewState: CollectionPointMapViewState) {
        if (!::map.isInitialized) {
            return
        }
        val changeSet = MapChangeSet(this, collectionPointMapViewState.collectionPoints)
        changeSet.updateLayer()
        if (changeSet.hasExistingIds.not() && changeSet.hasNewIds) {
            zoomToAllPins()
        }
        selectedMarker?.let {
            it.setIcon(unselectedIcon)
            it.setAnchor(0.5f, 1f)
            it.zIndex = 1f
        }
        collectionPointMapViewState.selectedCollectionPoint?.let { selectedCollectionPoint ->
            showCollectionPointView(selectedCollectionPoint)
        } ?: hideCollectionPointView()
    }

    override fun onMarkerClick(p0: Marker?): Boolean {
        (p0?.tag as? String)?.let {
            rootDispatch(SelectCollectionPointAction(it))
        }
        return true
    }

    override fun removePins(toRemove: Set<String>) {
        toRemove.map { markers.find { it.tag == it } }.forEach {
            it?.remove()
            markers.remove(it)
        }
    }

    override fun addPins(pins: Collection<CollectionPoint>) {
        pins.forEach { addMarker(it.id, LatLng(it.lat, it.lon)) }
    }

    private fun addMarker(id: String, latLng: LatLng) {
        val marker = map.addMarker(MarkerOptions().position(latLng).icon(unselectedIcon))
        marker.tag = id
        markers.add(marker)
    }

    override fun onTransitionStarted(motionLayout: MotionLayout, startId: Int, endId: Int) {}

    override fun onTransitionChange(motionLayout: MotionLayout, startId: Int, endId: Int, progress: Float) {}

    override fun onTransitionCompleted(motionLayout: MotionLayout, currentId: Int) {
        if (currentId == R.id.start) {
            viewBinding.collectionPointViewMotion.visibility = View.GONE
            if (selectedMarker != null) {
                rootDispatch(DeselectCollectionPointAction(null))
            }
        }
    }

    override fun onTransitionTrigger(motionLayout: MotionLayout, triggerId: Int, positive: Boolean, progress: Float) {}

    private fun hideCollectionPointView() {
        selectedMarker = null
        if (viewBinding.collectionPointViewMotion.currentState == viewBinding.collectionPointViewMotion.endState) {
            viewBinding.collectionPointViewMotion.transitionToStart()
        }
    }

    private fun showCollectionPointView(selectedCollectionPoint: CollectionPointViewState) {
        bindCollectionPointView(selectedCollectionPoint, viewBinding.collectionPointView)
        val marker = markers.first { it.tag == selectedCollectionPoint.collectionPoint.id }
        marker.setIcon(selectedIcon)
        marker.setAnchor(0.5f, 0.75f)
        marker.zIndex = 2f
        selectedMarker = marker
        map.animateCamera(CameraUpdateFactory.newLatLng(marker.position))
        if (viewBinding.collectionPointViewMotion.currentState == -1 ||
            viewBinding.collectionPointViewMotion.currentState == viewBinding.collectionPointViewMotion.startState
        ) {
            viewBinding.collectionPointViewMotion.visibility = View.VISIBLE
            viewBinding.collectionPointViewMotion.transitionToEnd()
        }
    }

    private fun bindCollectionPointView(
        collectionPointViewState: CollectionPointViewState,
        collectionPointView: ViewCollectionPointBinding
    ) {
        collectionPointView.title.text = collectionPointViewState.title
        collectionPointView.collectionTypesText.text = collectionPointViewState.collectionPointTypeTitle(Localizer(requireContext()))
        collectionPointView.collectionTypesIcons.removeAllViews()
        collectionPointViewState.collectionPointTypes.forEach { collectionPointType ->
            addCollectionTypeIcon(collectionPointType, collectionPointView)
        }
        collectionPointView.wheelchairAccessibleIcon.setImageResource(
            requireContext().getDrawableIdentifier(collectionPointViewState.wheelChairAccessibleIcon)
        )
        collectionPointView.wheelchairAccessibleText.text = requireContext().getString(collectionPointViewState.wheelChairAccessibleTitle)
        collectionPointView.wheelchairAccessibleIcon.alpha = collectionPointViewState.wheelChairTransparency
        collectionPointView.wheelchairAccessibleText.alpha = collectionPointViewState.wheelChairTransparency
        collectionPointView.address.text = collectionPointViewState.address
        collectionPointView.navigationLink.text = requireContext().getString(collectionPointViewState.navigationLink.text)
        collectionPointView.navigationLink.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(GoogleMapsUrlBuilder.getUrlToRoute(null, collectionPointViewState.navigationLink.payload))
            startActivity(intent)
        }
    }

    private fun addCollectionTypeIcon(collectionPointType: CollectionPointType, collectionPointView: ViewCollectionPointBinding) {
        val imageView = ImageView(requireContext())
        val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.marginEnd = ViewUtils.dp2px(requireContext(), 8f)
        imageView.layoutParams = layoutParams
        imageView.setBackgroundResource(R.drawable.round_icon_background)
        imageView.setImageResource(requireContext().getDrawableIdentifier(collectionPointType.icon))
        imageView.setPadding(ViewUtils.dp2px(requireContext(), 6f))
        collectionPointView.collectionTypesIcons.addView(imageView)
    }
}