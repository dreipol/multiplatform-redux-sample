/*
 * Copyright (c) 2021 dreipol GmbH
 *
 * Licensed under a Creative Commons Attribution-NonCommercial 4.0 International License. A copy of the license is provided with this code.
 *
 * No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
 */

package ch.dreipol.multiplatform.reduxsample.shared.ui

import ch.dreipol.dreimultiplatform.getLocalizedDayShort
import ch.dreipol.dreimultiplatform.getLocalizedMonthName
import ch.dreipol.dreimultiplatform.reduxkotlin.navigation.NavigationDirection
import ch.dreipol.multiplatform.reduxsample.shared.delight.Disposal
import ch.dreipol.multiplatform.reduxsample.shared.redux.thunk.loadDisposalsThunk
import kotlinx.datetime.*

data class CalendarViewState(
    val disposalsState: DisposalsState = DisposalsState(),
    val zip: Int? = null,
    val titleReplaceable: String = "calendar_next_disposal",
    val bellCDReplaceable: String = "calendar_bell_contentdescription",
    val disposalImageCDReplaceable: String = "disposal_image_contentdescription",
)

data class DisposalsState(
    val nextDisposals: List<DisposalCalendarEntry> = emptyList(),
    val disposals: List<DisposalCalendarMonth> = emptyList(),
    val loaded: Boolean = false
)

data class DisposalCalendarMonth(val monthYear: Pair<Month, Int>, val disposalCalendarEntries: List<DisposalCalendarEntry>) {
    val formattedHeader: String
        get() {
            val (month, year) = monthYear
            return "${getLocalizedMonthName(month.number)} $year"
        }
}

data class DisposalCalendarEntry(val disposal: Disposal, val showNotification: Boolean) {
    val notificationIconId: String
        get() = if (showNotification) "ic_24_notification_on" else "ic_24_notification_off"
    val locationReplaceable = "calendar_next_disposal_location"
    private val todayTemplate = "calendar_disposal_today"
    private val tomorrowTemplate = "calendar_disposal_tomorrow"

    fun buildTimeString(stringFromTemplate: (template: String) -> String): String {
        val date = disposal.date
        val now = Clock.System.now().toLocalDateTime(TimeZone.UTC).date
        val tomorrow = now.plus(DatePeriod(days = 1))
        if (now == date) {
            return stringFromTemplate.invoke(todayTemplate).toUpperCase()
        }
        if (tomorrow == date) {
            return stringFromTemplate.invoke(tomorrowTemplate).toUpperCase()
        }
        return "${getLocalizedDayShort(date.dayOfWeek.isoDayNumber)} ${date.dayOfMonth}.${date.monthNumber}."
    }
}

interface CalendarView : BaseView {
    fun render(viewState: CalendarViewState)
    override fun presenter() = calendarPresenter
}

val calendarPresenter = presenter<CalendarView> {
    {
        select({ it.calendarViewState }) {
            render(state.calendarViewState)
            if (state.calendarViewState.disposalsState.loaded.not()) {
                store.dispatch(loadDisposalsThunk())
            }
        }
        select({ it.navigationState }) {
            if (state.navigationState.navigationDirection == NavigationDirection.POP) {
                render(state.calendarViewState)
            }
        }
    }
}