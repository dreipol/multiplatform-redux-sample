/*
 * Copyright (c) 2021 dreipol GmbH
 *
 * Licensed under a Creative Commons Attribution-NonCommercial 4.0 International License. A copy of the license is provided with this code.
 *
 * No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
 */

package ch.dreipol.multiplatform.reduxsample.shared.redux.middleware

import ch.dreipol.multiplatform.reduxsample.shared.redux.AppState
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.NavigationAction
import ch.dreipol.multiplatform.reduxsample.shared.redux.thunk.loadSavedSettingsThunk
import ch.dreipol.multiplatform.reduxsample.shared.redux.thunk.saveOnboardingThunk
import org.reduxkotlin.middleware

fun onboardingMiddleware() = middleware<AppState> { store, next, action ->
    when (action) {
        NavigationAction.ONBOARDING_START -> {
            next(action)
            store.dispatch(loadSavedSettingsThunk())
        }
        NavigationAction.ONBOARDING_END -> {
            next(action)
            store.dispatch(saveOnboardingThunk())
        }
        else -> next(action)
    }
}