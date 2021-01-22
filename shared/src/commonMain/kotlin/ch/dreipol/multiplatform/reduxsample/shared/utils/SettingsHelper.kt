package ch.dreipol.multiplatform.reduxsample.shared.utils

import com.russhwolf.settings.Settings
import com.russhwolf.settings.invoke

object SettingsHelper {
    private const val LANGUAGE = "language"
    private const val SHOW_ONBOARDING = "show_onboarding"
    private const val RATING_SHOWED = "rating_showed"

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
}