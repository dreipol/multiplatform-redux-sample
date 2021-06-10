/*
 * Copyright (c) 2021 dreipol GmbH
 *
 * Licensed under a Creative Commons Attribution-NonCommercial 4.0 International License. A copy of the license is provided with this code.
 *
 * No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
 */

package ch.dreipol.multiplatform.reduxsample.shared.redux.middleware

import ch.dreipol.multiplatform.reduxsample.shared.redux.AppState
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.OpenedWithReminderNotification
import ch.dreipol.multiplatform.reduxsample.shared.utils.SettingsHelper
import ch.dreipol.multiplatform.reduxsample.shared.utils.getAppConfiguration
import org.reduxkotlin.middleware

fun storeRatingMiddleware() = middleware<AppState> { _, next, action ->

    when (action) {
        is OpenedWithReminderNotification -> {
            if (SettingsHelper.hasRatingShown().not()) {
                showRating()
            }
            Unit
        }
        else -> next(action)
    }
}

private fun showRating() {
    getAppConfiguration().platformFeatures.showRatingDialog()
    SettingsHelper.setRatingShowed(true)
}