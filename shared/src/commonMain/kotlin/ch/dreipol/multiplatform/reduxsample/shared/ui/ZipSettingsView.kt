package ch.dreipol.multiplatform.reduxsample.shared.ui

import ch.dreipol.dreimultiplatform.reduxkotlin.rootDispatch
import ch.dreipol.multiplatform.reduxsample.shared.redux.thunk.setNewZipThunk

data class ZipSettingsViewState(
    val headerViewState: HeaderViewState = HeaderViewState("settings_zip"),
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
            val selectedZip = viewState.enterZipViewState.selectedZip
            if (state.settingsState.state?.settings?.zip != selectedZip && viewState.enterZipViewState.invalidZip.not()) {
                selectedZip?.let { rootDispatch(setNewZipThunk(it)) }
            }
        }
    }
}