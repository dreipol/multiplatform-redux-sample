package ch.dreipol.multiplatform.reduxsample.shared.redux.actions

import ch.dreipol.multiplatform.reduxsample.shared.delight.CollectionPoint

data class CollectionPointsLoadedAction(val collectionPoints: List<CollectionPoint>)