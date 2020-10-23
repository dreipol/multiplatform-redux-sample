package ch.dreipol.multiplatform.reduxsample.shared.ui

data class InfoViewState(val titleHtmlKey: String = "info_title", val textHtmlKey: String = "info_text")

interface InfoView : BaseView {
    override fun presenter() = infoPresenter

    fun render(infoViewState: InfoViewState)
}

val infoPresenter = presenter<InfoView> {
    {
        select({ it.infoViewState }) { render(state.infoViewState) }
    }
}