/*
 * Copyright (c) 2021 dreipol GmbH
 *
 * Licensed under a Creative Commons Attribution-NonCommercial 4.0 International License. A copy of the license is provided with this code.
 *
 * No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
 */

package ch.dreipol.multiplatform.reduxsample.shared.utils

enum class AppLanguage(val shortName: String, val descriptionKey: String) {
    GERMAN("de", "settings_language_de"), ENGLISH("en", "settings_language_en");

    companion object {
        fun fromValue(shortName: String?): AppLanguage {
            return if (shortName.equals(GERMAN.shortName, ignoreCase = true)) {
                GERMAN
            } else {
                ENGLISH
            }
        }

        fun fromSettingsOrDefault(): AppLanguage {
            return SettingsHelper.getLanguage()?.let { fromValue(it) } ?: fromLocale()
        }
    }
}

expect fun AppLanguage.Companion.fromLocale(): AppLanguage