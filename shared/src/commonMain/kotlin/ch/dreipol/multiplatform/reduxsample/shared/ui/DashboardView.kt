package ch.dreipol.multiplatform.reduxsample.shared.ui

import ch.dreipol.multiplatform.reduxsample.shared.delight.Disposal
import ch.dreipol.multiplatform.reduxsample.shared.redux.loadDisposalsThunk

data class DashboardViewState(val disposalsState: DisposalsState = DisposalsState())
data class DisposalsState(val disposals: List<Disposal> = emptyList(), val loaded: Boolean = false)

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