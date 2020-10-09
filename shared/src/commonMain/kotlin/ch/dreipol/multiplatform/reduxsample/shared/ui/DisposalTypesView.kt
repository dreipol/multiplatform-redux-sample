package ch.dreipol.multiplatform.reduxsample.shared.ui

import ch.dreipol.multiplatform.reduxsample.shared.database.DisposalType

data class DisposalTypesViewState(
    val headerViewState: HeaderViewState = HeaderViewState("disposal_types_header_title"),
    val description: String = "disposal_types_description",
    val selectedDisposalTypes: List<DisposalType> = emptyList()
)

interface DisposalTypesView : BaseView {
    fun render(viewState: DisposalTypesViewState)
    override fun presenter() = disposalTypesPresenter
}

val disposalTypesPresenter = presenter<DisposalTypesView> {
    {
        select({ it.dashboardViewState.disposalTypesState }) {
            render(state.dashboardViewState.disposalTypesState)
        }
    }
}