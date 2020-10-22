package ch.dreipol.multiplatform.reduxsample.shared.ui

import ch.dreipol.dreimultiplatform.reduxkotlin.rootDispatch
import ch.dreipol.multiplatform.reduxsample.shared.redux.setNewZipThunk

data class ZipSettingsViewState(
    val enterZipViewState: EnterZipViewState = EnterZipViewState()
)

interface ZipSettingsView : BaseView {
    override fun presenter() = zipSettingsPresenter

    fun render(zipSettingsViewState: ZipSettingsViewState)
}

val zipSettingsPresenter = presenter<ZipSettingsView> {
    {
        select({ it.settingsViewState.zipSettingsViewState }) {
            val viewState = state.settingsViewState.zipSettingsViewState
            render(viewState)
            if (viewState.enterZipViewState.invalidZip.not()) {
                viewState.enterZipViewState.selectedZip?.let { rootDispatch(setNewZipThunk(it)) }
            }
        }
    }
}