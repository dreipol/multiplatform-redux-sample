package ch.dreipol.multiplatform.reduxsample.shared.database

import platform.Foundation.NSDateComponents
import kotlinx.datetime.toNSDateComponents

fun Reminder.remindDateComponents(): NSDateComponents {
    return this.remindDateTime.toNSDateComponents()
}