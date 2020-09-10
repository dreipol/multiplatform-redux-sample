package ch.dreipol.multiplatform.reduxsample.shared.database

import ch.dreipol.multiplatform.reduxsample.shared.delight.Database
import ch.dreipol.multiplatform.reduxsample.shared.utils.getAppConfiguration
import com.squareup.sqldelight.db.SqlDriver

interface DriverCreator {
    fun createDriver(): SqlDriver
}

expect class DriverFactory : DriverCreator {
    override fun createDriver(): SqlDriver
}

object DatabaseHelper {
    val database = Database(getAppConfiguration().driver)
}