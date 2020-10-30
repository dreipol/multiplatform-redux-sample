package ch.dreipol.multiplatform.reduxsample.shared.redux.actions

import ch.dreipol.multiplatform.reduxsample.shared.ui.DisposalNotification

data class DisposalsLoadedAction(val disposals: List<DisposalNotification>)