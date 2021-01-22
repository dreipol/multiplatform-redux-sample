package ch.dreipol.multiplatform.reduxsample.shared.ui

import ch.dreipol.multiplatform.reduxsample.shared.database.DisposalType

data class CalendarSettingsViewState(
    val headerViewState: HeaderViewState = HeaderViewState("settings_calendar_title"),
    val introductionKey: String = "settings_calendar_description",
    val selectedDisposalTypes: List<DisposalType> = emptyList(),
    val disposalToggleCDReplaceableKey: String = "disposal_toggle_contentdescription",
)

interface CalendarSettingsView : BaseView {
    fun render(viewState: CalendarSettingsViewState)
    override fun presenter() = calendarSettingsPresenter
}

val calendarSettingsPresenter = presenter<CalendarSettingsView> {
    {
        select({ it.settingsViewState.calendarSettingsViewState }) {
            render(state.settingsViewState.calendarSettingsViewState)
        }
    }
}