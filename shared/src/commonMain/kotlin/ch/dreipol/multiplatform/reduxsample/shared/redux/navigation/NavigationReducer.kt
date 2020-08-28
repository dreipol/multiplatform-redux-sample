package ch.dreipol.multiplatform.reduxsample.shared.redux.navigation

import ch.dreipol.dreimultiplatform.reduxkotlin.navigation.NavigationState
import ch.dreipol.dreimultiplatform.reduxkotlin.navigation.navigateBack
import ch.dreipol.multiplatform.reduxsample.shared.redux.NavigationAction
import org.reduxkotlin.Reducer

val navigationReducer: Reducer<NavigationState> = { state, action ->
    when (action) {
        NavigationAction.BACK -> navigateBack(state)
        else -> state
    }
}