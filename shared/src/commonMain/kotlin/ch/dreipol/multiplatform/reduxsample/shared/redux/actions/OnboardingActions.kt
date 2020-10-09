package ch.dreipol.multiplatform.reduxsample.shared.redux.actions

import ch.dreipol.multiplatform.reduxsample.shared.database.DisposalType

data class ZipUpdatedAction(val zip: Int?)

data class UpdateShowDisposalType(val disposalType: DisposalType, val show: Boolean)

data class UpdateAddNotification(val addNotification: Boolean)