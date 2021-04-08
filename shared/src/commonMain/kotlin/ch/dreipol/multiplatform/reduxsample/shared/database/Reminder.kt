package ch.dreipol.multiplatform.reduxsample.shared.database

import ch.dreipol.multiplatform.reduxsample.shared.delight.Disposal
import ch.dreipol.multiplatform.reduxsample.shared.utils.createWithEveningTime
import kotlinx.datetime.*

private fun createTime(remindTime: RemindTime, nextDisposalDate: LocalDate): LocalDateTime {
    return createWithEveningTime(nextDisposalDate.plus(-remindTime.daysBefore, DateTimeUnit.DAY))
}

enum class NotificationCategory(val key: String) {
    SAME_DAY("reminder_same_day"),
    DAY_BEFORE("reminder_day_before"),
    SEVERAL_DAYS_BEFORE("reminder_several_days_before");

    companion object {
        fun createFrom(remindDate: LocalDate, disposalDate: LocalDate): NotificationCategory {
            val daysUntil = remindDate.daysUntil(disposalDate)
            return when {
                daysUntil <= 0 -> SAME_DAY
                daysUntil == 1 -> DAY_BEFORE
                else -> SEVERAL_DAYS_BEFORE
            }
        }
    }
}

fun RemindTime.toNotificationCategory(): NotificationCategory {
    return when (this) {
        RemindTime.EVENING_BEFORE -> NotificationCategory.DAY_BEFORE
        RemindTime.TWO_DAYS_BEFORE, RemindTime.THREE_DAYS_BEFORE -> NotificationCategory.SEVERAL_DAYS_BEFORE
    }
}

data class Reminder(val remindDateTime: LocalDateTime, val notificationCategory: NotificationCategory, val disposals: List<Disposal>) {
    constructor(remindTime: RemindTime, disposals: List<Disposal>) :
        this(createTime(remindTime, disposals.first().date), remindTime.toNotificationCategory(), disposals)

    constructor(remindDateTime: LocalDateTime, disposals: List<Disposal>) :
        this(remindDateTime, NotificationCategory.createFrom(remindDateTime.date, disposals.first().date), disposals)
}