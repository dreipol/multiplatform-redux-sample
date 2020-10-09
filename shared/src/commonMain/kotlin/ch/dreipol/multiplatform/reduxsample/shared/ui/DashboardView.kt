package ch.dreipol.multiplatform.reduxsample.shared.ui

import ch.dreipol.multiplatform.reduxsample.shared.delight.Disposal
import ch.dreipol.multiplatform.reduxsample.shared.redux.loadDisposalsThunk

data class DashboardViewState(val disposalsState: DisposalsState = DisposalsState())
data class DisposalsState(val disposals: List<DisposalNotification> = emptyList(), val loaded: Boolean = false) {
    companion object {
        fun fromDisposals(disposals: List<Disposal>, state: DisposalsState): DisposalsState {
            // TODO use localized month name
            // TODO set showNotification correctly
            val disposalNotifications = disposals.map { DisposalNotification(it, true) }
            return state.copy(disposals = disposalNotifications, loaded = true)
        }
    }
}

data class DisposalNotification(val disposal: Disposal, val showNotification: Boolean) {
    val formattedDate: String
        get() {
            val date = disposal.date
            return "${date.dayOfMonth}.${date.monthNumber}."
        }
    val formattedHeader: String
        get() = "${disposal.date.month.name} ${disposal.date.year}"
    val notificationIconId: String
        get() = if (showNotification) "ic_icon_ic_24_notification_on" else "ic_icon_ic_24_notifications_off"
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
    }
}