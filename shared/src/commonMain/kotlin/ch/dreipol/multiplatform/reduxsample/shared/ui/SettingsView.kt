package ch.dreipol.multiplatform.reduxsample.shared.ui

data class SettingsViewState(
    val titleKey: String = "settings_title",
    val settings: List<String> = listOf("settings_zip", "settings_notifications", "settings_calendar", "settings_language")
)

interface SettingsView : BaseView {
    override fun presenter() = settingsPresenter

    fun render(settingsViewState: SettingsViewState)
}

val settingsPresenter = presenter<SettingsView> {
    {
        select({ it.settingsState }) { render(state.settingsViewState) }
    }
}