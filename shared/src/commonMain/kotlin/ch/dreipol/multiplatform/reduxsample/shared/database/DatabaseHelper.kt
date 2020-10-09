package ch.dreipol.multiplatform.reduxsample.shared.database

import ch.dreipol.multiplatform.reduxsample.shared.delight.Database
import ch.dreipol.multiplatform.reduxsample.shared.delight.Disposal
import ch.dreipol.multiplatform.reduxsample.shared.delight.NotificationSettings
import ch.dreipol.multiplatform.reduxsample.shared.delight.Settings
import ch.dreipol.multiplatform.reduxsample.shared.utils.getAppConfiguration
import com.squareup.sqldelight.ColumnAdapter
import com.squareup.sqldelight.db.SqlDriver
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toLocalDate

interface DriverCreator {
    fun createDriver(): SqlDriver
}

expect class DriverFactory : DriverCreator {
    override fun createDriver(): SqlDriver
}

object DatabaseHelper {
    val database = Database(
        driver = getAppConfiguration().driver,
        disposalAdapter = Disposal.Adapter(
            disposalTypeAdapter = DisposalTypeAdapter(),
            dateAdapter = DateAdapter()
        ),
        settingsAdapter = Settings.Adapter(
            showDisposalTypesAdapter = DisposalListAdapter()
        ),
        notificationSettingsAdapter = NotificationSettings.Adapter(
            disposalTypesAdapter = DisposalListAdapter()
        )
    )
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