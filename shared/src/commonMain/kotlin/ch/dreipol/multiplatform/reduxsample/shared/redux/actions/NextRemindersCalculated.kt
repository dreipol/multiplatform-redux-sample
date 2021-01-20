package ch.dreipol.multiplatform.reduxsample.shared.redux.actions

import ch.dreipol.multiplatform.reduxsample.shared.database.Reminder

data class NextRemindersCalculated(val nextReminders: List<Reminder>) : Action