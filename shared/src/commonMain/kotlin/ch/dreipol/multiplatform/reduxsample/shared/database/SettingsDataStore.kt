package ch.dreipol.multiplatform.reduxsample.shared.database

import ch.dreipol.multiplatform.reduxsample.shared.delight.NotificationSettings
import ch.dreipol.multiplatform.reduxsample.shared.delight.Settings

class SettingsDataStore {

    companion object {
        const val UNDEFINED_ID = 0L
    }

    fun getSettings(): Settings? {
        return DatabaseHelper.database.settingsQueries.find().executeAsOneOrNull()
    }

    fun insertOrUpdate(settings: Settings) {
        if (settings.id == UNDEFINED_ID) {
            DatabaseHelper.database.settingsQueries.insert(settings.zip)
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
                    notificationSettings.hoursBefore
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