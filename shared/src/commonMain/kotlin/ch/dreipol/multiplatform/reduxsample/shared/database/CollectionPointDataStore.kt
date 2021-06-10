/*
 * Copyright (c) 2021 dreipol GmbH
 *
 * Licensed under a Creative Commons Attribution-NonCommercial 4.0 International License. A copy of the license is provided with this code.
 *
 * No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
 */

package ch.dreipol.multiplatform.reduxsample.shared.database

import ch.dreipol.multiplatform.reduxsample.shared.delight.CollectionPoint
import ch.dreipol.multiplatform.reduxsample.shared.ui.MapFilterItem
import ch.dreipol.multiplatform.reduxsample.shared.utils.CollectionPointsImporter

class CollectionPointDataStore {

    fun findAll(): List<CollectionPoint> {
        return DatabaseHelper.database.collectionPointQueries.findAll().executeAsList()
    }

    fun findWithCollectionPointTypes(collectionPointTypes: List<MapFilterItem>): List<CollectionPoint> {
        val hasGlass = if (collectionPointTypes.first { it.collectionPointType == CollectionPointType.GLASS }.isSelected) true else null
        val hasOil = if (collectionPointTypes.first { it.collectionPointType == CollectionPointType.OIL }.isSelected) true else null
        val hasMetal = if (collectionPointTypes.first { it.collectionPointType == CollectionPointType.METAL }.isSelected) true else null
        return DatabaseHelper.database.collectionPointQueries.findByCollectionPointType(hasGlass, hasOil, hasMetal).executeAsList()
    }

    fun insertAll() {
        DatabaseHelper.database.transaction {
            CollectionPointsImporter.readCollectionPoints().forEach {
                DatabaseHelper.database.collectionPointQueries.insert(it)
            }
        }
    }
}