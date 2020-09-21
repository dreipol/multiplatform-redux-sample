package ch.dreipol.multiplatform.reduxsample.shared.database

import ch.dreipol.multiplatform.reduxsample.shared.delight.Database
import ch.dreipol.multiplatform.reduxsample.shared.delight.Disposal
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