package ch.dreipol.multiplatform.reduxsample.shared.redux

import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.DisposalsLoadedAction
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.SettingsLoadedAction
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.ZipUpdatedAction
import ch.dreipol.multiplatform.reduxsample.shared.redux.navigation.*
import ch.dreipol.multiplatform.reduxsample.shared.ui.DashboardViewState
import ch.dreipol.multiplatform.reduxsample.shared.ui.OnboardingViewState
import ch.dreipol.multiplatform.reduxsample.shared.ui.SelectDisposalTypesState
import org.reduxkotlin.Reducer

val rootReducer: Reducer<AppState> = { state, action ->
    val navigationState = navigationReducer(state.navigationState, action)
    var newState = state.copy(navigationState = navigationState)
    newState = when (navigationState.screens.last()) {
        MainScreen.DASHBOARD -> newState.copy(dashboardViewState = dashboardViewReducer(newState.dashboardViewState, action))
        is OnboardingScreen -> newState.copy(onboardingViewState = onboardingViewReducer(newState.onboardingViewState, action))
        else -> newState
    }
    newState
}

val dashboardViewReducer: Reducer<DashboardViewState> = { state, action ->
    when (action) {
        is DisposalsLoadedAction -> state.copy(disposalsState = state.disposalsState.copy(disposals = action.disposals, loaded = true))
        else -> state
    }
}

val onboardingViewReducer: Reducer<OnboardingViewState> = { state, action ->
    when (action) {
        is SettingsLoadedAction ->
            state.copy(
                enterZipState = state.enterZipState.copy(selectedZip = action.settings.zip),
                selectDisposalTypesState = SelectDisposalTypesState.fromSettings(action.settings)
            )
        is ZipUpdatedAction -> state.copy(enterZipState = state.enterZipState.copy(selectedZip = action.zip))
        else -> state
    }
}