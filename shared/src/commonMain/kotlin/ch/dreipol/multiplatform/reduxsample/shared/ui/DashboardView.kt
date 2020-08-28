package ch.dreipol.multiplatform.reduxsample.shared.ui

data class DashboardViewState(val text: String = "dashboard")

interface DashboardView : BaseView {
    fun render(viewState: DashboardViewState)
    override fun presenter() = dashboardPresenter
}

val dashboardPresenter = presenter<DashboardView> {
    {
        select({ it.dashboardViewState }) { render(state.dashboardViewState) }
    }
}