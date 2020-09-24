package ch.dreipol.multiplatform.reduxsample.shared.redux.middleware

import ch.dreipol.multiplatform.reduxsample.shared.redux.AppState
import ch.dreipol.multiplatform.reduxsample.shared.redux.loadSavedSettings
import ch.dreipol.multiplatform.reduxsample.shared.redux.navigation.NavigationAction
import ch.dreipol.multiplatform.reduxsample.shared.redux.saveOnboardingThunk
import org.reduxkotlin.middleware

fun onboardingMiddleware() = middleware<AppState> { store, next, action ->
    when (action) {
        NavigationAction.ONBOARDING_START -> {
            next(action)
            store.dispatch(loadSavedSettings())
        }
        NavigationAction.ONBOARDING_END -> {
            next(action)
            store.dispatch(saveOnboardingThunk())
        }
        else -> next(action)
    }
}