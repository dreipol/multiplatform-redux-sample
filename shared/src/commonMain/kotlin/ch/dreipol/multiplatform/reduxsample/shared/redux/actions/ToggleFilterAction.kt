package ch.dreipol.multiplatform.reduxsample.shared.redux.actions

import ch.dreipol.multiplatform.reduxsample.shared.database.CollectionPointType

data class ToggleFilterAction(val filter: CollectionPointType) : Action