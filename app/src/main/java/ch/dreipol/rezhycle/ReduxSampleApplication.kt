/*
 * Copyright (c) 2021 dreipol GmbH
 *
 * Licensed under a Creative Commons Attribution-NonCommercial 4.0 International License. A copy of the license is provided with this code.
 *
 * No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
 */

package ch.dreipol.rezhycle

import android.app.Application
import ch.dreipol.dreimultiplatform.PlatformFeatures
import ch.dreipol.multiplatform.reduxsample.shared.database.DriverFactory
import ch.dreipol.multiplatform.reduxsample.shared.redux.ReduxSampleApp
import ch.dreipol.multiplatform.reduxsample.shared.utils.AppConfiguration
import ch.dreipol.multiplatform.reduxsample.shared.utils.FileReader
import ch.dreipol.multiplatform.reduxsample.shared.utils.initApp

class ReduxSampleApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initApp(AppConfiguration(ReduxSampleApp(), DriverFactory(this), PlatformFeatures(), FileReader(this)))
    }
}