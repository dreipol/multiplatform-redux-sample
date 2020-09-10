package ch.dreipol.multiplatform.reduxsample.shared.database

import ch.dreipol.multiplatform.reduxsample.shared.delight.Database
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

actual class DriverFactory : DriverCreator {
    actual override fun createDriver(): SqlDriver {
        return NativeSqliteDriver(Database.Schema, "app.db")
    }
}