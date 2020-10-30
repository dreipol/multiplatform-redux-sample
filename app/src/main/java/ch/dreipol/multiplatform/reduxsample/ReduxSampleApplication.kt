package ch.dreipol.multiplatform.reduxsample

import android.app.Application
import ch.dreipol.multiplatform.reduxsample.shared.database.DriverFactory
import ch.dreipol.multiplatform.reduxsample.shared.redux.ReduxSampleApp
import ch.dreipol.multiplatform.reduxsample.shared.utils.AppConfiguration
import ch.dreipol.multiplatform.reduxsample.shared.utils.AppLanguage
import ch.dreipol.multiplatform.reduxsample.shared.utils.initApp
import java.util.*

class ReduxSampleApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initApp(AppConfiguration(ReduxSampleApp(AppLanguage.fromValue(Locale.getDefault().language)), DriverFactory(this)))
    }
}