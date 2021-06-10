/*
 * Copyright (c) 2021 dreipol GmbH
 *
 * Licensed under a Creative Commons Attribution-NonCommercial 4.0 International License. A copy of the license is provided with this code.
 *
 * No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
 */

package ch.dreipol.rezhycle.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import ch.dreipol.multiplatform.reduxsample.shared.utils.AppLanguage
import ch.dreipol.rezhycle.MainActivity
import java.util.*

fun updateResources(context: Context, appLanguage: AppLanguage): Context? {
    val locale = Locale(appLanguage.shortName)
    Locale.setDefault(locale)
    val overrideConfiguration: Configuration = Configuration(context.resources.configuration)
    overrideConfiguration.setLocale(locale)
    return context.createConfigurationContext(overrideConfiguration)
}

fun restartApplication(mainActivity: Activity) {
    val refresh = Intent(mainActivity, MainActivity::class.java)
    mainActivity.finish()
    mainActivity.startActivity(refresh)
}