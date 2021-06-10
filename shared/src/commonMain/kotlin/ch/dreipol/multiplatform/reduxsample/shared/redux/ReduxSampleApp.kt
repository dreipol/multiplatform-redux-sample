/*
 * Copyright (c) 2021 dreipol GmbH
 *
 * Licensed under a Creative Commons Attribution-NonCommercial 4.0 International License. A copy of the license is provided with this code.
 *
 * No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
 */

package ch.dreipol.multiplatform.reduxsample.shared.redux

import ch.dreipol.dreimultiplatform.reduxkotlin.presenterEnhancer
import ch.dreipol.dreimultiplatform.uiDispatcher
import ch.dreipol.multiplatform.reduxsample.shared.redux.middleware.*
import ch.dreipol.multiplatform.reduxsample.shared.redux.reducer.rootReducer
import ch.dreipol.multiplatform.reduxsample.shared.redux.thunk.initSettingsThunk
import ch.dreipol.multiplatform.reduxsample.shared.redux.thunk.initialNavigationThunk
import org.reduxkotlin.applyMiddleware
import org.reduxkotlin.compose
import org.reduxkotlin.createThreadSafeStore
import org.reduxkotlin.createThunkMiddleware

class ReduxSampleApp() {
    val store = createThreadSafeStore(
        rootReducer,
        AppState.initialState(),
        compose(
            listOf(
                presenterEnhancer(uiDispatcher),
                applyMiddleware(
                    coroutineMiddleware(uiDispatcher),
                    convertThunkActionMiddleware(),
                    loggerMiddleware(),
                    createThunkMiddleware(),
                    onboardingMiddleware(),
                    storeRatingMiddleware(),
                ),
            )
        )
    )

    init {
        store.dispatch(initSettingsThunk())
        store.dispatch(initialNavigationThunk())
    }
}