package ch.dreipol.multiplatform.reduxsample.shared.redux.middleware

import ch.dreipol.multiplatform.reduxsample.shared.redux.AppState
import ch.dreipol.multiplatform.reduxsample.shared.redux.OnboardingScreen
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.NavigationAction
import org.reduxkotlin.middleware

fun notificationPermissionMiddleware() = middleware<AppState> { store, next, action ->
    if (action == NavigationAction.ONBOARDING_NEXT && store.state.navigationState.currentScreen == OnboardingScreen(3)) {
        print("Check permissions")
    }
    next(action)
}