/*
 * Copyright (c) 2021 dreipol GmbH
 *
 * Licensed under a Creative Commons Attribution-NonCommercial 4.0 International License. A copy of the license is provided with this code.
 *
 * No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
 */

package ch.dreipol.multiplatform.reduxsample.shared.utils

import android.content.Context

actual class FileReader(private val context: Context) {
    actual fun readCollectionPointsFile(): String {
        return context.assets.open("poi_sammelstelle_view.json").bufferedReader().readText()
    }
}