package ch.dreipol.multiplatform.reduxsample.shared.database

import ch.dreipol.multiplatform.reduxsample.shared.delight.CollectionPoint
import ch.dreipol.multiplatform.reduxsample.shared.utils.CollectionPointsImporter

class CollectionPointDataStore {

    fun findAll(): List<CollectionPoint> {
        val result = DatabaseHelper.database.collectionPointQueries.findAll().executeAsList()
        if (result.isEmpty()) {
            insertAll()
            return DatabaseHelper.database.collectionPointQueries.findAll().executeAsList()
        }
        return result
    }

    private fun insertAll() {
        DatabaseHelper.database.transaction {
            CollectionPointsImporter.readCollectionPoints().forEach {
                DatabaseHelper.database.collectionPointQueries.insert(it)
            }
        }
    }
}