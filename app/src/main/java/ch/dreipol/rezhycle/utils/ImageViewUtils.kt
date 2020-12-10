package ch.dreipol.rezhycle.utils

import android.content.Context

fun Context.getDrawableIdentifier(imageIdentifier: String): Int {
    return resources.getIdentifier(imageIdentifier, "drawable", packageName)
}