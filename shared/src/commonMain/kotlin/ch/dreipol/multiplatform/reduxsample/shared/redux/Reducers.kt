package ch.dreipol.multiplatform.reduxsample.shared.redux

import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.DisposalsLoadedAction
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.SettingsLoadedAction
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.UpdateShowDisposalType
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.ZipUpdatedAction
import ch.dreipol.multiplatform.reduxsample.shared.redux.navigation.navigationReducer
import ch.dreipol.multiplatform.reduxsample.shared.ui.DashboardViewState
import ch.dreipol.multiplatform.reduxsample.shared.ui.OnboardingViewState
import ch.dreipol.multiplatform.reduxsample.shared.ui.SelectDisposalTypesState
import org.reduxkotlin.Reducer

val rootReducer: Reducer<AppState> = { state, action ->
    val navigationState = navigationReducer(state.navigationState, action)
    val dashboardViewState = dashboardViewReducer(state.dashboardViewState, action)
    val onboardingViewState = onboardingViewReducer(state.onboardingViewState, action)
    state.copy(navigationState = navigationState, dashboardViewState = dashboardViewState, onboardingViewState = onboardingViewState)
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
        is UpdateShowDisposalType -> state.copy(
            selectDisposalTypesState = SelectDisposalTypesState.update(state.selectDisposalTypesState, action.disposalType, action.show)
        )
        else -> state
    }
}