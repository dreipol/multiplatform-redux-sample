package ch.dreipol.multiplatform.reduxsample.shared.redux.actions

import ch.dreipol.multiplatform.reduxsample.shared.database.DisposalType

data class ZipUpdatedAction(val zip: Int?)

data class UpdateNotifyDisposalType(val disposalType: DisposalType, val notify: Boolean)

data class UpdateAddNotification(val addNotification: Boolean)