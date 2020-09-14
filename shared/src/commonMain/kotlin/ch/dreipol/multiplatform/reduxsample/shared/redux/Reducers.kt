package ch.dreipol.multiplatform.reduxsample.shared.redux

import ch.dreipol.multiplatform.reduxsample.shared.redux.navigation.MainScreen
import ch.dreipol.multiplatform.reduxsample.shared.redux.navigation.navigationReducer
import ch.dreipol.multiplatform.reduxsample.shared.ui.DashboardViewState
import org.reduxkotlin.Reducer

val rootReducer: Reducer<AppState> = { state, action ->
    val navigationState = navigationReducer(state.navigationState, action)
    var newState = state.copy(navigationState = navigationState)
    newState = when (navigationState.screens.last()) {
        MainScreen.DASHBOARD -> newState.copy(dashboardViewState = dashboardViewReducer(newState.dashboardViewState, action))
        else -> newState
    }
    newState
}

val dashboardViewReducer: Reducer<DashboardViewState> = { state, action ->
    when (action) {
        is DisposalsChanged -> state.copy(disposals = action.disposals)
        else -> state
    }
}