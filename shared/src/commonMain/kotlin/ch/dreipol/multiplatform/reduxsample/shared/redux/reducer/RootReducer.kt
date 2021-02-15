package ch.dreipol.multiplatform.reduxsample.shared.redux.reducer

import ch.dreipol.multiplatform.reduxsample.shared.redux.AppState
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.AppLanguageUpdated
import org.reduxkotlin.Reducer

val rootReducer: Reducer<AppState> = { state, action ->
    val appLanguage = if (action is AppLanguageUpdated) action.appLanguage else state.appLanguage
    val navigationState = navigationReducer(state.navigationState, action)
    val settingsState = settingsReducer(state.settingsState, action)
    val calendarViewState = calendarViewReducer(state.calendarViewState, action)
    val settingsViewState = settingsViewReducer(state.settingsViewState, action)
    val onboardingViewState = onboardingViewReducer(state.onboardingViewState, action)
    state.copy(
        appLanguage = appLanguage, navigationState = navigationState, settingsState = settingsState, calendarViewState = calendarViewState,
        settingsViewState = settingsViewState, onboardingViewState = onboardingViewState
    )
}


