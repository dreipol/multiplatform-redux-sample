/*
 * Copyright (c) 2021 dreipol GmbH
 *
 * Licensed under a Creative Commons Attribution-NonCommercial 4.0 International License. A copy of the license is provided with this code.
 *
 * No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
 */

package ch.dreipol.multiplatform.reduxsample.shared.database

import ch.dreipol.multiplatform.reduxsample.shared.delight.NotificationSettings
import ch.dreipol.multiplatform.reduxsample.shared.delight.Settings
import ch.dreipol.multiplatform.reduxsample.shared.utils.now
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

fun NotificationSettings.getNextReminders(zip: Int): List<Reminder> {
    val now = LocalDateTime.now()
    var minDate = now.date
    if (now >= todayEvening) {
        // If current time is already 18:00 disposals today are irrelevant for notifications
        minDate = minDate.plus(1, DateTimeUnit.DAY)
    }
    minDate = minDate.plus(remindTime.daysBefore, DateTimeUnit.DAY)
    val futureDisposals = DisposalDataStore().getFutureDisposals(minDate, zip, disposalTypes)
    return futureDisposals.groupBy { it.date }.map { Reminder(remindTime, it.value) }
}

enum class RemindTime(val descriptionKey: String, val daysBefore: Int) {
    EVENING_BEFORE("remind_time_evening_before", 1),
    TWO_DAYS_BEFORE("remind_time_2_days_before", 2),
    THREE_DAYS_BEFORE("remind_time_3_days_before", 3),
}