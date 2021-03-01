package ch.dreipol.multiplatform.reduxsample.shared.database

import kotlinx.datetime.LocalDateTime

class AdditionalReminderDataStore {
    fun getFutureReminders(minDate: LocalDateTime): List<Reminder> {
        val database = DatabaseHelper.database
        val additionalReminders = database.additionalReminderQueries.findAfter(minDate).executeAsList()
        val disposalIds = additionalReminders.map { it.disposalId }
        val disposals = database.disposalQueries.getMany(disposalIds).executeAsList().associateBy { it.id }
        return additionalReminders.mapNotNull {
            disposals[it.disposalId]?.let { disposal ->
                Reminder(it.date, listOf(disposal))
            }
        }
    }

    fun create(disposalId: String, dateTime: LocalDateTime) {
        DatabaseHelper.database.additionalReminderQueries.insert(dateTime, disposalId)
    }
}