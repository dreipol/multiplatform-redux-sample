package ch.dreipol.multiplatform.reduxsample.shared.database

import kotlinx.datetime.toNSDateComponents
import platform.Foundation.NSDateComponents

fun Reminder.remindDateComponents(): NSDateComponents {
    return this.remindDateTime.toNSDateComponents()
}