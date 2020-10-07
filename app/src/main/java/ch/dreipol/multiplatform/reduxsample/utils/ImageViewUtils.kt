package ch.dreipol.multiplatform.reduxsample.utils

import android.content.Context

fun Context.getDrawableIdentifier(imageIdentifier: String): Int {
    return resources.getIdentifier(imageIdentifier, "drawable", packageName)
}