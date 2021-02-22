package ch.dreipol.multiplatform.reduxsample.shared.utils

import ch.dreipol.dreimultiplatform.kermit
import platform.Foundation.NSBundle
import platform.Foundation.NSString
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.stringWithContentsOfFile

actual class FileReader {
    actual fun readCollectionPointsFile(): String {
        val filePath = NSBundle.mainBundle().pathForResource("poi_sammelstelle_view", "json")
        val string = NSString.stringWithContentsOfFile(filePath.toString(), NSUTF8StringEncoding, null) ?: return ""
        kermit().d({ "string of json: $string" })
        return string
    }
}