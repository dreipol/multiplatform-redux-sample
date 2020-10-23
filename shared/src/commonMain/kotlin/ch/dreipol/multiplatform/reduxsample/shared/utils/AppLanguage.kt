package ch.dreipol.multiplatform.reduxsample.shared.utils

enum class AppLanguage(val shortName: String) {
    GERMAN("de"), ENGLISH("en");

    companion object {
        fun fromValue(shortName: String?): AppLanguage {
            return if (shortName.equals(ENGLISH.shortName, ignoreCase = true)) {
                ENGLISH
            } else {
                GERMAN
            }
        }
    }
}