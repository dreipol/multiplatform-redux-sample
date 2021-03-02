package ch.dreipol.multiplatform.reduxsample.shared.utils

import ch.dreipol.multiplatform.reduxsample.shared.utils.SnoozeUnit.*
import kotlinx.datetime.DateTimePeriod

data class SnoozeNotification(val unit: SnoozeUnit, val duration: Int)

enum class SnoozeUnit {
    SECONDS,
    MINUTES,
    HOURS,
    DAYS;

    companion object {
        fun fromValue(value: String?): SnoozeUnit? {
            return when (value?.toLowerCase()) {
                SECONDS.name.toLowerCase() -> SECONDS
                MINUTES.name.toLowerCase() -> MINUTES
                HOURS.name.toLowerCase() -> HOURS
                DAYS.name.toLowerCase() -> DAYS
                else -> null
            }
        }
    }
}

fun SnoozeNotification.asDateTimePeriod(): DateTimePeriod {
    return when (this.unit) {
        SECONDS -> DateTimePeriod(seconds = duration.toLong())
        MINUTES -> DateTimePeriod(minutes = duration)
        HOURS -> DateTimePeriod(hours = duration)
        DAYS -> DateTimePeriod(days = duration)
    }
}