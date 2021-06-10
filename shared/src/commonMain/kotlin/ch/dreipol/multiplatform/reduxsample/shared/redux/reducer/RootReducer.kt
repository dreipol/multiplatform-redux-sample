/*
 * Copyright (c) 2021 dreipol GmbH
 *
 * Licensed under a Creative Commons Attribution-NonCommercial 4.0 International License. A copy of the license is provided with this code.
 *
 * No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
 */

package ch.dreipol.multiplatform.reduxsample.shared.redux.reducer

import ch.dreipol.multiplatform.reduxsample.shared.redux.AppState
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.AppLanguageUpdated
import ch.dreipol.multiplatform.reduxsample.shared.redux.freezeState
import org.reduxkotlin.Reducer

val rootReducer: Reducer<AppState> = { state, action ->
    val appLanguage = if (action is AppLanguageUpdated) action.appLanguage else state.appLanguage
    val navigationState = navigationReducer(state.navigationState, action)
    val settingsState = settingsReducer(state.settingsState, action)
    val calendarViewState = calendarViewReducer(state.calendarViewState, action)
    val collectionPointMapViewState = collectionPointMapViewReducer(state.collectionPointMapViewState, action)
    val settingsViewState = settingsViewReducer(state.settingsViewState, action)
    val onboardingViewState = onboardingViewReducer(state.onboardingViewState, action)
    val newState = state.copy(
        appLanguage = appLanguage,
        navigationState = navigationState,
        settingsState = settingsState,
        calendarViewState = calendarViewState,
        collectionPointMapViewState = collectionPointMapViewState,
        settingsViewState = settingsViewState,
        onboardingViewState = onboardingViewState
    )
    newState.freezeState()
}