package ch.dreipol.multiplatform.reduxsample.shared.database

import android.content.Context
import ch.dreipol.multiplatform.reduxsample.shared.delight.Database
import ch.dreipol.multiplatform.reduxsample.shared.utils.AppConfiguration
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

actual class DriverFactory(private val context: Context) : DriverCreator {
    actual override fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(Database.Schema, context, AppConfiguration.databaseFileName)
    }
}