package ch.dreipol.multiplatform.reduxsample.shared.utils

import ch.dreipol.multiplatform.reduxsample.shared.delight.CollectionPoint

interface MapIconLayer {
    val iconIds: Set<String>

    fun removeIcons(toRemove: Set<String>)

    fun addIcon(id: String, lat: Double, lon: Double, pinKind: PinKind)
}

enum class PinKind(val icon: String) {
    SELECTED("ic_24_location_selected"),
    UNSELECTED("ic_24_location"),
}

class MapChangeSet(val layer: MapIconLayer, val newPoints: List<CollectionPoint>, val pinKind: PinKind) {

    private val existingIds: Set<String> by lazy { layer.iconIds }
    private val newIds: Set<String> by lazy { newPoints.map { it.id }.toSet() }

    private val addIds: Set<String>
        get() = newIds - existingIds

    private val removeIds: Set<String>
        get() = existingIds - newIds

    fun updateLayer() {
        layer.removeIcons(removeIds)
        newPoints.filter { addIds.contains(it.id) }.forEach { layer.addIcon(it.id, it.lat, it.lon, pinKind) }
    }
}