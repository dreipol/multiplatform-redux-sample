package ch.dreipol.multiplatform.reduxsample.shared.redux

import ch.dreipol.dreimultiplatform.reduxkotlin.navigation.Screen

enum class MainScreen : Screen {
    DASHBOARD,
    INFORMATION,
    SETTINGS,
    ZIP_SETTINGS,
    CALENDAR_SETTINGS,
    NOTIFICATION_SETTINGS,
    LANGUAGE_SETTINGS,
}

data class OnboardingScreen(val step: Int = 1) : Screen {
    val canGoBack: Boolean
        get() = step != 1
}