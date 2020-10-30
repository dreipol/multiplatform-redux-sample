package ch.dreipol.multiplatform.reduxsample.shared.utils
import platform.Foundation.NSLocale
import platform.Foundation.currentLocale
import platform.Foundation.languageCode

actual fun AppLanguage.Companion.fromLocale(): AppLanguage {
    return fromValue(NSLocale.currentLocale.languageCode)
}