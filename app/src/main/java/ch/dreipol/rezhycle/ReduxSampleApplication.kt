package ch.dreipol.rezhycle

import android.app.Application
import ch.dreipol.multiplatform.reduxsample.shared.database.DriverFactory
import ch.dreipol.multiplatform.reduxsample.shared.redux.ReduxSampleApp
import ch.dreipol.multiplatform.reduxsample.shared.utils.AppConfiguration
import ch.dreipol.multiplatform.reduxsample.shared.utils.FileReader
import ch.dreipol.multiplatform.reduxsample.shared.utils.initApp

class ReduxSampleApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initApp(AppConfiguration(ReduxSampleApp(), DriverFactory(this), FileReader(this)))
    }
}