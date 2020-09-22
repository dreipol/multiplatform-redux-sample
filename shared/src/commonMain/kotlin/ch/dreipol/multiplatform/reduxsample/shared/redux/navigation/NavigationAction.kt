package ch.dreipol.multiplatform.reduxsample.shared.redux.navigation

enum class NavigationAction {
    BACK,
    DASHBOARD,
    ONBOARDING_NEXT,
}

data class OnboardingNavigation(val step: Int = 1)