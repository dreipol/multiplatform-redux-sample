package ch.dreipol.multiplatform.reduxsample.shared.database

import ch.dreipol.multiplatform.reduxsample.shared.delight.Disposal
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class DisposalDataStore {

    fun setNewDisposals(disposals: List<Disposal>) {
        disposals.forEach { DatabaseHelper.database.disposalQueries.insertOrUpdate(it) }
    }

    fun findTodayOrInFuture(
        zip: Int,
        disposalTypes: List<DisposalType>,
        minDate: LocalDate = Clock.System.now().toLocalDateTime(TimeZone.UTC).date
    ): List<Disposal> {
        return DatabaseHelper.database.disposalQueries.byZip(zip).executeAsList().filter { it.date >= minDate }
            .filter { disposalTypes.contains(it.disposalType) }
            .sortedWith(compareBy({ it.date }, { it.disposalType.ordinal }))
    }

    fun getNextDisposals(minDate: LocalDate, zip: Int, disposalTypes: List<DisposalType>): List<Disposal> {
        val futureDisposals = findTodayOrInFuture(zip, disposalTypes, minDate)
        val nextDisposalDate = futureDisposals.firstOrNull()?.date
        return nextDisposalDate?.let { futureDisposals.filter { it.date == nextDisposalDate } } ?: emptyList()
    }

    fun getAllZips(): List<Int> {
        return DatabaseHelper.database.disposalQueries.allZips().executeAsList()
    }
}