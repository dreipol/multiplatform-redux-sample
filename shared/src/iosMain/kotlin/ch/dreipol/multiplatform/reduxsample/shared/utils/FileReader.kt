/*
 * Copyright (c) 2021 dreipol GmbH
 *
 * Licensed under a Creative Commons Attribution-NonCommercial 4.0 International License. A copy of the license is provided with this code.
 *
 * No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
 */

package ch.dreipol.multiplatform.reduxsample.shared.utils

import platform.Foundation.NSBundle
import platform.Foundation.NSString
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.stringWithContentsOfFile

actual class FileReader {
    actual fun readCollectionPointsFile(): String {
        val filePath = NSBundle.mainBundle().pathForResource("poi_sammelstelle_view", "json")
        return NSString.stringWithContentsOfFile(filePath.toString(), NSUTF8StringEncoding, null) ?: return ""
    }
}