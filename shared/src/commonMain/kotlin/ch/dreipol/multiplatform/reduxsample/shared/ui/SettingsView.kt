package ch.dreipol.multiplatform.reduxsample.shared.ui

interface SettingsView : BaseView {
    override fun presenter() = settingsPresenter
}

val settingsPresenter = presenter<SettingsView> {
    {
    }
}