package ch.dreipol.multiplatform.reduxsample.shared.redux.actions

import ch.dreipol.multiplatform.reduxsample.shared.ui.MapFilterItem

data class UpdateFilterAction(val newFilter: List<MapFilterItem>) : Action