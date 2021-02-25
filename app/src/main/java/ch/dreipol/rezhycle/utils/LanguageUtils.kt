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