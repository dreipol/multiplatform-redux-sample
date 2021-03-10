package ch.dreipol.multiplatform.reduxsample.shared.redux.actions

data class SelectCollectionPointAction(val collectionPointId: String) : Action, PrintableAction

object DeselectCollectionPointAction : Action
