package ch.dreipol.multiplatform.reduxsample.shared.redux.middleware

import ch.dreipol.multiplatform.reduxsample.shared.redux.AppState
import ch.dreipol.multiplatform.reduxsample.shared.redux.loadSavedSettingsThunk
import ch.dreipol.multiplatform.reduxsample.shared.redux.navigation.NavigationAction
import ch.dreipol.multiplatform.reduxsample.shared.redux.saveOnboardingThunk
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