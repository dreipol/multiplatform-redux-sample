package ch.dreipol.multiplatform.reduxsample.shared.utils

import ch.dreipol.multiplatform.reduxsample.shared.delight.Disposal
import kotlinx.datetime.*

val todayEvening: LocalDateTime
    get() = createWithEveningTime(Clock.System.now().toLocalDateTime(TimeZone.UTC).date)

fun createWithEveningTime(localDate: LocalDate): LocalDateTime {
    return LocalDateTime(localDate.year, localDate.month, localDate.dayOfMonth, 18, 0, 0, 0)
}

fun formatDisposalDateForNotification(disposal: Disposal): String {
    return "${disposal.date.dayOfMonth}.${disposal.date.monthNumber}.${disposal.date.year}"
}