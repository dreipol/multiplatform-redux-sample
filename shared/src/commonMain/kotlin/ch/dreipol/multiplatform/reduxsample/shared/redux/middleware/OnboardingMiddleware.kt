package ch.dreipol.multiplatform.reduxsample.shared.redux.middleware

import ch.dreipol.multiplatform.reduxsample.shared.redux.AppState
import ch.dreipol.multiplatform.reduxsample.shared.redux.navigation.NavigationAction
import ch.dreipol.multiplatform.reduxsample.shared.redux.navigation.OnboardingNavigation
import ch.dreipol.multiplatform.reduxsample.shared.redux.navigation.OnboardingScreen
import org.reduxkotlin.middleware

fun onboardingMiddleware() = middleware<AppState> { store, next, action ->
    when (action) {
        NavigationAction.ONBOARDING_NEXT -> {
            val lastScreen = store.state.navigationState.screens.last()
            if (lastScreen !is OnboardingScreen) {
                throw IllegalStateException("current screen is not an onboarding-screen: $lastScreen")
            }
            if (lastScreen.step < OnboardingScreen.LAST_ONBOARDING_STEP) {
                store.dispatch(OnboardingNavigation(lastScreen.step + 1))
            } else {
                store.dispatch(NavigationAction.DASHBOARD)
            }
        }
        else -> next(action)
    }
}