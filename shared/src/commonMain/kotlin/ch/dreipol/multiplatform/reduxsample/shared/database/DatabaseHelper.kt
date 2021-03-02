package ch.dreipol.multiplatform.reduxsample.shared.database

import ch.dreipol.multiplatform.reduxsample.shared.delight.*
import ch.dreipol.multiplatform.reduxsample.shared.utils.getAppConfiguration
import com.squareup.sqldelight.ColumnAdapter
import com.squareup.sqldelight.db.SqlDriver
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toLocalDate

interface DriverCreator {
    fun createDriver(): SqlDriver
}

expect class DriverFactory : DriverCreator {
    override fun createDriver(): SqlDriver
}

object DatabaseHelper {
    private const val CURRENT_DB_VERSION = 2

    val database = Database(
        driver = getAppConfiguration().driver,
        disposalAdapter = Disposal.Adapter(
            disposalTypeAdapter = DisposalTypeAdapter(),
            dateAdapter = DateAdapter()
        ),
        settingsAdapter = Settings.Adapter(
            showDisposalTypesAdapter = DisposalListAdapter(),
            defaultRemindTimeAdapter = RemindTimeAdapter()
        ),
        notificationSettingsAdapter = NotificationSettings.Adapter(
            disposalTypesAdapter = DisposalListAdapter(),
            remindTimeAdapter = RemindTimeAdapter()
        ),
        additionalReminderAdapter = AdditionalReminder.Adapter(
            dateAdapter = DateTimeAdapter()
        )
    )

    init {
        Database.Schema.migrate(getAppConfiguration().driver, Database.Schema.version, CURRENT_DB_VERSION)
    }
}

class DisposalTypeAdapter : ColumnAdapter<DisposalType, String> {
    override fun decode(databaseValue: String): DisposalType {
        return DisposalType.valueOf(databaseValue)
    }

    override fun encode(value: DisposalType): String {
        return value.name
    }
}

class DateAdapter : ColumnAdapter<LocalDate, String> {
    override fun decode(databaseValue: String): LocalDate {
        return databaseValue.toLocalDate()
    }

    override fun encode(value: LocalDate): String {
        return value.toString()
    }
}

class DateTimeAdapter : ColumnAdapter<LocalDateTime, String> {
    override fun decode(databaseValue: String): LocalDateTime {
        return LocalDateTime.parse(databaseValue)
    }

    override fun encode(value: LocalDateTime): String {
        return value.toString()
    }
}

class DisposalListAdapter : ColumnAdapter<List<DisposalType>, String> {
    override fun decode(databaseValue: String): List<DisposalType> {
        if (databaseValue.isEmpty()) {
            return emptyList()
        }
        return databaseValue.split(",").map { DisposalType.valueOf(it) }
    }

    override fun encode(value: List<DisposalType>): String {
        return value.joinToString(separator = ",") { it.name }
    }
}

class RemindTimeAdapter : ColumnAdapter<RemindTime, String> {
    override fun decode(databaseValue: String): RemindTime {
        return RemindTime.valueOf(databaseValue)
    }

    override fun encode(value: RemindTime): String {
        return value.name
    }
}