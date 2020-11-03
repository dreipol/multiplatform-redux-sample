package ch.dreipol.multiplatform.reduxsample.shared.redux.actions

import ch.dreipol.multiplatform.reduxsample.shared.database.Reminder

data class NextReminderCalculated(val nextReminder: Reminder?)