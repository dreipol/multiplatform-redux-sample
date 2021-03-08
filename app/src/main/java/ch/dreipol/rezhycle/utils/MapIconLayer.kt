package ch.dreipol.rezhycle.utils

import ch.dreipol.multiplatform.reduxsample.shared.utils.MapIconLayer
import io.openmobilemaps.mapscore.shared.map.layers.icon.IconLayerInterface

class MapIconLayer(val iconLayer: IconLayerInterface) : MapIconLayer {
    override val iconIds: Set<String>
        get() = iconLayer.getIcons().map { it.getIdentifier() }.toSet()

    override fun removeIcons(toRemove: Set<String>) {
        TODO("Not yet implemented")
    }

    override fun addIcon(id: String, lat: Double, lon: Double, icon: String) {
        TODO("Not yet implemented")
    }
}