package ch.dreipol.multiplatform.reduxsample.shared.redux.actions

import ch.dreipol.multiplatform.reduxsample.shared.ui.DisposalCalendarMonth

data class DisposalsLoadedAction(val disposals: List<DisposalCalendarMonth>) : Action