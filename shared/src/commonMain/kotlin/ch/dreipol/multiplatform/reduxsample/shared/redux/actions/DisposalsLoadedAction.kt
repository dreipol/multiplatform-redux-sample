package ch.dreipol.multiplatform.reduxsample.shared.redux.actions

import ch.dreipol.multiplatform.reduxsample.shared.delight.Disposal

data class DisposalsLoadedAction(val disposals: List<Disposal>)