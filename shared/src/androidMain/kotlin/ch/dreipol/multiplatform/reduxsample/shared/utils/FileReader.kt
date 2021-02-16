package ch.dreipol.multiplatform.reduxsample.shared.utils

import android.content.Context

actual class FileReader(private val context: Context) {
    actual fun readCollectionPointsFile(): String {
        return context.assets.open("poi_sammelstelle_view.json").bufferedReader().readText()
    }
}