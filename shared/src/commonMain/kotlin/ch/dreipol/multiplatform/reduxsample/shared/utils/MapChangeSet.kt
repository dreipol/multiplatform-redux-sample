/*
 * Copyright (c) 2021 dreipol GmbH
 *
 * Licensed under a Creative Commons Attribution-NonCommercial 4.0 International License. A copy of the license is provided with this code.
 *
 * No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
 */

package ch.dreipol.multiplatform.reduxsample.shared.utils

import ch.dreipol.multiplatform.reduxsample.shared.delight.CollectionPoint

interface MapPinLayer {
    val pinIds: Set<String>

    fun removePins(toRemove: Set<String>)

    fun addPins(pins: Collection<CollectionPoint>)
}

enum class PinKind(val icon: String) {
    SELECTED("ic_24_location_selected"),
    UNSELECTED("ic_24_location"),
}

class MapChangeSet(val layer: MapPinLayer, val newPoints: List<CollectionPoint>) {

    private val existingIds: Set<String> by lazy { layer.pinIds }
    private val newIds: Set<String> by lazy { newPoints.map { it.id }.toSet() }

    private val addIds: Set<String>
        get() = newIds - existingIds

    private val removeIds: Set<String>
        get() = existingIds - newIds

    val hasExistingIds: Boolean = existingIds.isNotEmpty()
    val hasNewIds: Boolean = newIds.isNotEmpty()

    fun updateLayer() {
        layer.removePins(removeIds)
        layer.addPins(newPoints.filter { addIds.contains(it.id) })
    }
}