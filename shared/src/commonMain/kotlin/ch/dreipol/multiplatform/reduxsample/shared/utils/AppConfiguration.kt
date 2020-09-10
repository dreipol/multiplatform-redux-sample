package ch.dreipol.multiplatform.reduxsample.shared.utils

import ch.dreipol.multiplatform.reduxsample.shared.database.DriverFactory

private lateinit var appConfiguration: AppConfiguration

fun initApp(appConfig: AppConfiguration) {
    if (::appConfiguration.isInitialized) {
        throw IllegalStateException("App Configuration is already initialized")
    } else {
        appConfiguration = appConfig
    }
}

fun getAppConfiguration(): AppConfiguration {
    return appConfiguration
}

data class AppConfiguration(
    val driverFactory: DriverFactory
) {
    val driver = driverFactory.createDriver()
}