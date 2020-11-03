package ch.dreipol.multiplatform.reduxsample.shared.utils

import kotlinx.datetime.*

val todayEvening: LocalDateTime
    get() = createWithEveningTime(Clock.System.now().toLocalDateTime(TimeZone.UTC).date)

fun createWithEveningTime(localDate: LocalDate): LocalDateTime {
    return LocalDateTime(localDate.year, localDate.month, localDate.dayOfMonth, 18, 0, 0, 0)
}