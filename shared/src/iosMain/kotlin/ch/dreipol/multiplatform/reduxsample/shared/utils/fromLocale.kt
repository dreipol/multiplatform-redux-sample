package ch.dreipol.multiplatform.reduxsample.shared.utils
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toNSDate
import platform.Foundation.*

actual fun AppLanguage.Companion.fromLocale(): AppLanguage {
    return fromValue(NSLocale.currentLocale.languageCode)
}

//fun LocalDate.toiOSDate(): NSDate {
//    return this.toNSDate()
//}
////fun toNSDate(d: LocalDate): NSDate {
////    d.toNSDateComponents()
////}