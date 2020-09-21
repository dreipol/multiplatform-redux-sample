package ch.dreipol.multiplatform.reduxsample.shared.database

import ch.dreipol.multiplatform.reduxsample.shared.delight.Disposal

class DisposalDataStore {

    fun setNewDisposals(disposals: List<Disposal>) {
        disposals.forEach { DatabaseHelper.database.disposalQueries.insertOrUpdate(it) }
    }

    fun getAllDisposals(): List<Disposal> {
        return DatabaseHelper.database.disposalQueries.findAll().executeAsList()
    }
}