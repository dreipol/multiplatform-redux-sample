package ch.dreipol.multiplatform.reduxsample.shared.ui

interface InfoView : BaseView {
    override fun presenter() = infoPresenter
}

val infoPresenter = presenter<InfoView> {
    {
    }
}