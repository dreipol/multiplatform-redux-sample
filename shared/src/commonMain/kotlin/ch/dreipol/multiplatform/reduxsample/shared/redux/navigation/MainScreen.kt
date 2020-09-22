package ch.dreipol.multiplatform.reduxsample.shared.redux.navigation

import ch.dreipol.dreimultiplatform.reduxkotlin.navigation.Screen

enum class MainScreen : Screen {
    DASHBOARD
}

data class OnboardingScreen(val step: Int = 1) : Screen {
    companion object {
        const val LAST_ONBOARDING_STEP = 4
    }
}