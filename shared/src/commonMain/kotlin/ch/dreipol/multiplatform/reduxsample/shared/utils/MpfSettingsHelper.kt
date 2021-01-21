package ch.dreipol.multiplatform.reduxsample.shared.utils

import com.russhwolf.settings.Settings
import com.russhwolf.settings.invoke

object MpfSettingsHelper {
    private const val LANGUAGE = "language"
    private const val SHOW_ONBOARDING = "show_onboarding"

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
}