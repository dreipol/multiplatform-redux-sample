package ch.dreipol.multiplatform.reduxsample.shared.database

import ch.dreipol.multiplatform.reduxsample.shared.delight.Disposal
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class DisposalDataStore {

    fun setNewDisposals(disposals: List<Disposal>) {
        disposals.forEach { DatabaseHelper.database.disposalQueries.insertOrUpdate(it) }
    }

    fun findTodayOrInFuture(zip: Int, disposalTypes: List<DisposalType>): List<Disposal> {
        val today = Clock.System.now().toLocalDateTime(TimeZone.UTC).date
        return DatabaseHelper.database.disposalQueries.byZip(zip).executeAsList().filter { it.date >= today }
            .filter { disposalTypes.contains(it.disposalType) }
            .sortedWith(compareBy({ it.date }, { it.disposalType.ordinal }))
    }
}