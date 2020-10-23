package ch.dreipol.multiplatform.reduxsample

import android.app.Application
import android.content.Context
import ch.dreipol.multiplatform.reduxsample.shared.database.DriverFactory
import ch.dreipol.multiplatform.reduxsample.shared.redux.ReduxSampleApp
import ch.dreipol.multiplatform.reduxsample.shared.utils.AppConfiguration
import ch.dreipol.multiplatform.reduxsample.shared.utils.AppLanguage
import ch.dreipol.multiplatform.reduxsample.shared.utils.getAppConfiguration
import ch.dreipol.multiplatform.reduxsample.shared.utils.initApp
import ch.dreipol.multiplatform.reduxsample.utils.updateResources
import java.util.*

class ReduxSampleApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initApp(AppConfiguration(ReduxSampleApp(AppLanguage.fromValue(Locale.getDefault().language)), DriverFactory(this)))
    }

    override fun attachBaseContext(base: Context?) {
        base?.let {
            val resourceContext = updateResources(it, getAppConfiguration().reduxSampleApp.store.state.settingsState.appLanguage)
            super.attachBaseContext(resourceContext)
        } ?: run {
            super.attachBaseContext(base)
        }
    }
}