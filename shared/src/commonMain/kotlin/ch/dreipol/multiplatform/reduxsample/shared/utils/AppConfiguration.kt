/*
 * Copyright (c) 2021 dreipol GmbH
 *
 * Licensed under a Creative Commons Attribution-NonCommercial 4.0 International License. A copy of the license is provided with this code.
 *
 * No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
 */

package ch.dreipol.multiplatform.reduxsample.shared.utils

import ch.dreipol.dreimultiplatform.PlatformFeatures
import ch.dreipol.multiplatform.reduxsample.shared.database.DriverFactory
import ch.dreipol.multiplatform.reduxsample.shared.redux.ReduxSampleApp

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
    val reduxSampleApp: ReduxSampleApp,
    val driverFactory: DriverFactory,
    val platformFeatures: PlatformFeatures,
    val fileReader: FileReader,
) {
    companion object {
        val databaseFileName = "app.db"
    }

    val driver = driverFactory.createDriver()
}