package ch.dreipol.multiplatform.reduxsample.shared.ui

import ch.dreipol.dreimultiplatform.getLocalizedDayShort
import ch.dreipol.dreimultiplatform.getLocalizedMonthName
import ch.dreipol.dreimultiplatform.reduxkotlin.navigation.NavigationDirection
import ch.dreipol.multiplatform.reduxsample.shared.delight.Disposal
import ch.dreipol.multiplatform.reduxsample.shared.redux.loadDisposalsThunk
import kotlinx.datetime.isoDayNumber

data class DashboardViewState(
    val disposalsState: DisposalsState = DisposalsState(),
    val disposalTypesState: DisposalTypesViewState = DisposalTypesViewState(),
    val zip: Int? = null,
    val titleReplaceable: String = "dashboard_next_disposal",
    val notificationIcon: String = "ic_icon_ic_24_notification_on",
    val menuIcon: String = "ic_menu_24_px"
)

data class DisposalsState(
    val nextDisposals: List<DisposalNotification> = emptyList(),
    val disposals: List<DisposalNotification> = emptyList(),
    val loaded: Boolean = false
)

data class DisposalNotification(val disposal: Disposal, val showNotification: Boolean) {
    val formattedDate: String
        get() {
            val date = disposal.date
            return "${getLocalizedDayShort(date.dayOfWeek.isoDayNumber)} ${date.dayOfMonth}.${date.monthNumber}."
        }
    val formattedHeader: String
        get() = "${getLocalizedMonthName(disposal.date.monthNumber)} ${disposal.date.year}"
    val notificationIconId: String
        get() = if (showNotification) "ic_icon_ic_24_notification_on" else "ic_icon_ic_24_notifications_off"
    val locationReplaceable = "dashboard_next_disposal_location"
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