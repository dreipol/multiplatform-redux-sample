package ch.dreipol.multiplatform.reduxsample.shared.ui

data class ImprintViewState(
    val contentKey: String = "imprint_content"
)

interface ImprintView : BaseView {
    override fun presenter() = imprintPresenter

    fun render(imprintViewState: ImprintViewState)
}

val imprintPresenter = presenter<ImprintView> {
    {
        select({ it.settingsViewState.imprintViewState }) {
            val viewState = state.settingsViewState.imprintViewState
            render(viewState)
        }
    }
}