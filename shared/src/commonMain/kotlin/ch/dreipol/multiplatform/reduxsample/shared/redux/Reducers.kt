package ch.dreipol.multiplatform.reduxsample.shared.redux

import ch.dreipol.multiplatform.reduxsample.shared.redux.navigation.*
import ch.dreipol.multiplatform.reduxsample.shared.ui.DashboardViewState
import ch.dreipol.multiplatform.reduxsample.shared.ui.GenericOnboardingStep
import ch.dreipol.multiplatform.reduxsample.shared.ui.OnboardingViewState
import ch.dreipol.multiplatform.reduxsample.shared.ui.OnboardingZipStep
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
        is OnboardingNavigation -> createOnboardingViewState(action.step)
        else -> state
    }
}

private fun createOnboardingViewState(step: Int): OnboardingViewState {
    return when (step) {
        1 -> OnboardingZipStep(step, "ZIP", "next", emptyList())
        2, 3, -> GenericOnboardingStep(step, "generic", "next")
        4 -> GenericOnboardingStep(step, "generic", "finish")
        else -> throw IllegalStateException()
    }
}