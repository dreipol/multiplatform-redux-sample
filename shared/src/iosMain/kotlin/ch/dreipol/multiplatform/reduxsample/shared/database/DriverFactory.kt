package ch.dreipol.multiplatform.reduxsample.shared.database

import ch.dreipol.multiplatform.reduxsample.shared.delight.Database
import co.touchlab.sqliter.DatabaseConfiguration
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import com.squareup.sqldelight.drivers.native.wrapConnection
import platform.Foundation.NSFileManager

private val groupIdentifier = "group.ch.dreipol.reZHycle"

actual class DriverFactory : DriverCreator {
    actual override fun createDriver(): SqlDriver {
        val groupUrl = NSFileManager.defaultManager
            .containerURLForSecurityApplicationGroupIdentifier(groupIdentifier)
            ?: throw Exception("The iOS shared container could not be found")
        val schema = Database.Schema
        val config = DatabaseConfiguration(
            name = DatabaseHelper.fileName,
            basePath = groupUrl.path,
            version = schema.version,
            create = { connection ->
                wrapConnection(connection) { schema.create(it) }
            },
            upgrade = { connection, oldVersion, newVersion ->
                wrapConnection(connection) { schema.migrate(it, oldVersion, newVersion) }
            }
        )
        return NativeSqliteDriver(config)
    }
}