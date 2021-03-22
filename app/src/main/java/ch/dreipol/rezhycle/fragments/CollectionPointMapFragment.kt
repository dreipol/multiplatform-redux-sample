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
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.DeselectCollectionPointAction
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.SelectCollectionPointAction
import ch.dreipol.multiplatform.reduxsample.shared.ui.CollectionPointMapView
import ch.dreipol.multiplatform.reduxsample.shared.ui.CollectionPointMapViewState
import ch.dreipol.multiplatform.reduxsample.shared.ui.CollectionPointViewState
import ch.dreipol.multiplatform.reduxsample.shared.utils.MapChangeSet
import ch.dreipol.multiplatform.reduxsample.shared.utils.MapIconLayer
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

class CollectionPointMapFragment :
    BaseFragment<FragmentCollectionPointMapBinding, CollectionPointMapView>(),
    CollectionPointMapView,
    GoogleMap.OnMarkerClickListener,
    MapIconLayer,
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

    override val iconIds: Set<String>
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
        if (!::map.isInitialized) {
            return
        }
        val changeSet = MapChangeSet(this, collectionPointMapViewState.collectionPoints, PinKind.UNSELECTED)
        changeSet.updateLayer()
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

    override fun removeIcons(toRemove: Set<String>) {
        toRemove.map { markers.find { it.tag == it } }.forEach {
            it?.remove()
            markers.remove(it)
        }
    }

    override fun addIcon(id: String, lat: Double, lon: Double, pinKind: PinKind) {
        val latLng = LatLng(lat, lon)
        val bitmap = if (pinKind == PinKind.SELECTED) selectedIcon else unselectedIcon
        val marker = map.addMarker(
            MarkerOptions()
                .position(latLng)
                .icon(bitmap)
        )
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