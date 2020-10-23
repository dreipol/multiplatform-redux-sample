package ch.dreipol.multiplatform.reduxsample.shared.redux

import ch.dreipol.dreimultiplatform.reduxkotlin.navigation.Screen

enum class MainScreen : Screen {
    DASHBOARD,
    DISPOSAL_TYPES,
    INFORMATION,
    SETTINGS,
    ZIP_SETTINGS,
}

data class OnboardingScreen(val step: Int = 1) : Screen {
    val canGoBack: Boolean
        get() = step != 1
}