package ch.dreipol.multiplatform.reduxsample.shared.utils
import platform.Foundation.*

actual fun AppLanguage.Companion.fromLocale(): AppLanguage {
    return fromValue(NSLocale.currentLocale.languageCode)
}