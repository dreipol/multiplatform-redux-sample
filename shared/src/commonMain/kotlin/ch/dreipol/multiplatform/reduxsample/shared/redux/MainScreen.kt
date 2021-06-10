/*
 * Copyright (c) 2021 dreipol GmbH
 *
 * Licensed under a Creative Commons Attribution-NonCommercial 4.0 International License. A copy of the license is provided with this code.
 *
 * No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
 */

package ch.dreipol.multiplatform.reduxsample.shared.redux

import ch.dreipol.dreimultiplatform.reduxkotlin.navigation.Screen

enum class MainScreen : Screen {
    CALENDAR,
    COLLECTION_POINT_MAP,
    SETTINGS,
    ZIP_SETTINGS,
    CALENDAR_SETTINGS,
    LICENCES,
    IMPRINT,
    NOTIFICATION_SETTINGS,
    LANGUAGE_SETTINGS,
}

data class OnboardingScreen(val step: Int = 1) : Screen