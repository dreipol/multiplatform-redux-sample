package ch.dreipol.multiplatform.reduxsample.shared.utils

import java.util.*

actual fun AppLanguage.Companion.fromLocale(): AppLanguage {
    return fromValue(Locale.getDefault().language)
}