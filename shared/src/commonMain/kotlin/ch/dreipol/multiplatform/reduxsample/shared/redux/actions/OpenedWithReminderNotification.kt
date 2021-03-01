package ch.dreipol.multiplatform.reduxsample.shared.redux.actions

class OpenedWithReminderNotification : Action

data class SnoozeNotificationAction(val disposalID: String, val unit: String, val duration: Int) : Action