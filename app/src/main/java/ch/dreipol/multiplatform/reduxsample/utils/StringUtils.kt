package ch.dreipol.multiplatform.reduxsample.utils

import android.content.Context

fun Context.getString(identifier: String): String {
    val resourceId = resources.getIdentifier(identifier, "string", packageName)
    return resources.getString(resourceId)
}