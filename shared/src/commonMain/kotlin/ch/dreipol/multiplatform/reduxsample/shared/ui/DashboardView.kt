package ch.dreipol.multiplatform.reduxsample.shared.ui

import ch.dreipol.dreimultiplatform.getLocalizedDayShort
import ch.dreipol.dreimultiplatform.getLocalizedMonthName
import ch.dreipol.dreimultiplatform.reduxkotlin.navigation.NavigationDirection
import ch.dreipol.multiplatform.reduxsample.shared.delight.Disposal
import ch.dreipol.multiplatform.reduxsample.shared.redux.loadDisposalsThunk
import kotlinx.datetime.*

data class DashboardViewState(
    val disposalsState: DisposalsState = DisposalsState(),
    val zip: Int? = null,
    val titleReplaceable: String = "dashboard_next_disposal",
)

data class DisposalsState(
    val nextDisposals: List<DisposalNotification> = emptyList(),
    val disposals: Map<String, List<DisposalNotification>> = emptyMap(),
    val loaded: Boolean = false
)

data class DisposalNotification(val disposal: Disposal, val showNotification: Boolean) {
    val formattedHeader: String
        get() = "${getLocalizedMonthName(disposal.date.monthNumber)} ${disposal.date.year}"
    val notificationIconId: String
        get() = if (showNotification) "ic_24_notification_on" else "ic_24_notification_off"
    val locationReplaceable = "dashboard_next_disposal_location"
    private val todayTemplate = "dashboard_disposal_today"
    private val tomorrowTemplate = "dashboard_disposal_tomorrow"

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

interface DashboardView : BaseView {
    fun render(viewState: DashboardViewState)
    override fun presenter() = dashboardPresenter
}

val dashboardPresenter = presenter<DashboardView> {
    {
        select({ it.dashboardViewState }) {
            render(state.dashboardViewState)
            if (state.dashboardViewState.disposalsState.loaded.not()) {
                store.dispatch(loadDisposalsThunk())
            }
        }
        select({ it.navigationState }) {
            if (state.navigationState.navigationDirection == NavigationDirection.POP) {
                render(state.dashboardViewState)
            }
        }
    }
}