/*
 * Copyright (c) 2021 dreipol GmbH
 *
 * Licensed under a Creative Commons Attribution-NonCommercial 4.0 International License. A copy of the license is provided with this code.
 *
 * No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
 */

package ch.dreipol.multiplatform.reduxsample.shared.ui

import ch.dreipol.multiplatform.reduxsample.shared.database.DisposalType

data class CalendarSettingsViewState(
    val headerViewState: HeaderViewState = HeaderViewState("settings_calendar_title"),
    val introductionKey: String = "settings_calendar_description",
    val selectedDisposalTypes: List<DisposalType> = emptyList(),
    val disposalToggleCDReplaceableKey: String = "disposal_toggle_contentdescription",
    val disposalImageCDReplaceableKey: String = "disposal_image_contentdescription",
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