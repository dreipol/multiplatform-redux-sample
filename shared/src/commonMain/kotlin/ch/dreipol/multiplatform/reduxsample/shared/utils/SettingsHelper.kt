/*
 * Copyright (c) 2021 dreipol GmbH
 *
 * Licensed under a Creative Commons Attribution-NonCommercial 4.0 International License. A copy of the license is provided with this code.
 *
 * No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
 */

package ch.dreipol.multiplatform.reduxsample.shared.utils

import ch.dreipol.dreimultiplatform.reduxkotlin.permissions.NotificationPermission
import com.russhwolf.settings.Settings
import com.russhwolf.settings.invoke
import com.russhwolf.settings.set

object SettingsHelper {
    private const val LANGUAGE = "language"
    private const val SHOW_ONBOARDING = "show_onboarding"
    private const val RATING_SHOWED = "rating_showed"
    private const val NOTIFICATION_PERMISSION = "notification_permission"

    private val settings = Settings()

    fun setLanguage(language: String) {
        settings.putString(LANGUAGE, language)
    }

    fun getLanguage(): String? {
        return settings.getStringOrNull(LANGUAGE)
    }

    fun setShowOnboarding(showOnboarding: Boolean) {
        settings.putBoolean(SHOW_ONBOARDING, showOnboarding)
    }

    fun showOnboarding(): Boolean {
        return settings.getBoolean(SHOW_ONBOARDING, true)
    }

    fun setRatingShowed(ratingShowed: Boolean) {
        settings.putBoolean(RATING_SHOWED, ratingShowed)
    }

    fun hasRatingShown(): Boolean {
        return settings.getBoolean(RATING_SHOWED, false)
    }

    var notificationPermission: Int?
        get() {
            return settings.getIntOrNull(NOTIFICATION_PERMISSION)
        }
        set(value) {
            settings[NOTIFICATION_PERMISSION] = value
        }
}

fun NotificationPermission.Companion.fromSettingsOrDefault(): NotificationPermission {
    return SettingsHelper.notificationPermission?.let { NotificationPermission.fromInt(it) } ?: NotificationPermission.NOT_DETERMINED
}