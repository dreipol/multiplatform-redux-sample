package ch.dreipol.multiplatform.reduxsample.shared.redux.actions

import ch.dreipol.multiplatform.reduxsample.shared.ui.DisposalCalendarEntry

data class DisposalsLoadedAction(val disposals: Map<String, List<DisposalCalendarEntry>>)