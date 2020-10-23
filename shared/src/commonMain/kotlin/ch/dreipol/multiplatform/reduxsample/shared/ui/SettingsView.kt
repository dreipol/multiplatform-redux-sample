package ch.dreipol.multiplatform.reduxsample.shared.ui

import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.NavigationAction

data class SettingsViewState(
    val titleKey: String = "settings_title",
    val settings: List<SettingsEntry> = listOf(
        SettingsEntry("settings_zip", NavigationAction.ZIP_SETTINGS),
        SettingsEntry("settings_notifications", NavigationAction.ZIP_SETTINGS),
        SettingsEntry("settings_calendar", NavigationAction.CALENDAR_SETTINGS),
        SettingsEntry("settings_language", NavigationAction.ZIP_SETTINGS)
    ),
    val zipSettingsViewState: ZipSettingsViewState = ZipSettingsViewState(),
    val calendarSettingsViewState: CalendarSettingsViewState = CalendarSettingsViewState(),
    val notificationSettingsViewState: NotificationSettingsViewState = NotificationSettingsViewState(),
)

data class SettingsEntry(val descriptionKey: String, val navigationAction: NavigationAction)

interface SettingsView : BaseView {
    override fun presenter() = settingsPresenter

    fun render(settingsViewState: SettingsViewState)
}

val settingsPresenter = presenter<SettingsView> {
    {
        select({ it.settingsState }) { render(state.settingsViewState) }
    }
}