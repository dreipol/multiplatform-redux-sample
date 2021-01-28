package ch.dreipol.multiplatform.reduxsample.shared.database

import ch.dreipol.multiplatform.reduxsample.shared.delight.Database
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import co.touchlab.sqliter.DatabaseConfiguration
import platform.Foundation.NSFileManager
import platform.Foundation.URLByAppendingPathComponent

actual class DriverFactory : DriverCreator {
    actual override fun createDriver(): SqlDriver {
        val groupUrl = NSFileManager.defaultManager
            .containerURLForSecurityApplicationGroupIdentifier("group.ch.dreipol.reZHycle")
        print(groupUrl?.path)
        val config = DatabaseConfiguration(name = "app.db",
            basePath = groupUrl!!.path,
            version = Database.Schema.version,
            create = { _ -> })
        return NativeSqliteDriver(config)
    }
}