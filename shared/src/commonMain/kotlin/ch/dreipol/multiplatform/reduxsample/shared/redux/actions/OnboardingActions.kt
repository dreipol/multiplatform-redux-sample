package ch.dreipol.multiplatform.reduxsample.shared.redux.actions

import ch.dreipol.multiplatform.reduxsample.shared.database.DisposalType
import ch.dreipol.multiplatform.reduxsample.shared.database.RemindTime

data class UpdateShowDisposalType(val disposalType: DisposalType, val show: Boolean) : Action

//
data class UpdateAddNotification(val addNotification: Boolean) : Action

data class UpdateRemindTime(val remindTime: RemindTime) : Action