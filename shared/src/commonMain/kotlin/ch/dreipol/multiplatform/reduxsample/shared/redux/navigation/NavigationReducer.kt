package ch.dreipol.multiplatform.reduxsample.shared.redux.navigation

import ch.dreipol.dreimultiplatform.reduxkotlin.navigation.NavigationDirection
import ch.dreipol.dreimultiplatform.reduxkotlin.navigation.NavigationState
import ch.dreipol.dreimultiplatform.reduxkotlin.navigation.navigateBack
import ch.dreipol.multiplatform.reduxsample.shared.redux.NavigationActions
import org.reduxkotlin.Reducer

val navigationReducer: Reducer<NavigationState> = { state, action ->
    when (action) {
        NavigationActions.BACK -> navigateBack(state)
        NavigationActions.DASHBOARD -> {
            val screens = listOf(MainScreen.DASHBOARD)
            state.copy(screens = screens, navigationDirection = NavigationDirection.POP)
        }
        else -> state
    }
}