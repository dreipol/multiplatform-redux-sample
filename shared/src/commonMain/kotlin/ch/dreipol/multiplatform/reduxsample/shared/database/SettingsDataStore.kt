package ch.dreipol.multiplatform.reduxsample.shared.database

import ch.dreipol.multiplatform.reduxsample.shared.delight.NotificationSettings
import ch.dreipol.multiplatform.reduxsample.shared.delight.Settings
import ch.dreipol.multiplatform.reduxsample.shared.utils.createWithEveningTime
import ch.dreipol.multiplatform.reduxsample.shared.utils.todayEvening
import kotlinx.datetime.*

class SettingsDataStore {

    companion object {
        const val UNDEFINED_ID = 0L
        val defaultShownDisposalTypes = listOf(*DisposalType.values())
        val defaultRemindTime = RemindTime.EVENING_BEFORE
    }

    fun getSettings(): Settings? {
        return DatabaseHelper.database.settingsQueries.find().executeAsOneOrNull()
    }

    fun insertOrUpdate(settings: Settings) {
        if (settings.id == UNDEFINED_ID) {
            DatabaseHelper.database.settingsQueries.insert(settings.zip, settings.showDisposalTypes, settings.defaultRemindTime)
        } else {
            DatabaseHelper.database.settingsQueries.update(settings)
        }
    }

    fun getNotificationSettings(): List<NotificationSettings> {
        return DatabaseHelper.database.notification_settingsQueries.findAll().executeAsList()
    }

    fun insertOrUpdate(notificationSettings: NotificationSettings) {
        when {
            notificationSettings.id == UNDEFINED_ID -> {
                DatabaseHelper.database.notification_settingsQueries.insert(
                    notificationSettings.disposalTypes,
                    notificationSettings.remindTime
                )
            }
            notificationSettings.disposalTypes.isEmpty() -> {
                delete(notificationSettings)
            }
            else -> {
                DatabaseHelper.database.notification_settingsQueries.update(notificationSettings)
            }
        }
    }

    fun delete(notificationSettings: NotificationSettings) {
        DatabaseHelper.database.notification_settingsQueries.delete(notificationSettings.id)
    }

    fun deleteNotificationSettings() {
        DatabaseHelper.database.notification_settingsQueries.deleteAll()
    }
}

data class Reminder(val dateTime: LocalDateTime, val disposalTypes: List<DisposalType>)

fun NotificationSettings.getNextReminder(zip: Int): Reminder? {
    val now = Clock.System.now().toLocalDateTime(TimeZone.UTC)
    val minDate = if (now >= todayEvening) {
        now.date.plus(1, DateTimeUnit.DAY)
    } else {
        now.date
    }
    val nextDisposals = DisposalDataStore().getNextDisposals(minDate, zip, disposalTypes)
    val nextDisposalDate = nextDisposals.firstOrNull()?.date ?: return null
    val remindTime = when (remindTime) {
        RemindTime.EVENING_BEFORE -> createWithEveningTime(nextDisposalDate.plus(-1, DateTimeUnit.DAY))
        RemindTime.TWO_DAYS_BEFORE -> createWithEveningTime(nextDisposalDate.plus(-2, DateTimeUnit.DAY))
        RemindTime.THREE_DAYS_BEFORE -> createWithEveningTime(nextDisposalDate.plus(-3, DateTimeUnit.DAY))
    }
    return Reminder(remindTime, nextDisposals.map { it.disposalType })
}

enum class RemindTime(val descriptionKey: String) {
    EVENING_BEFORE("remind_time_evening_before"),
    TWO_DAYS_BEFORE("remind_time_2_days_before"),
    THREE_DAYS_BEFORE("remind_time_3_days_before"),
}