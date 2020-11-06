package ch.dreipol.multiplatform.reduxsample.shared.redux.reducer

import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.PossibleZipsLoaded
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.SettingsLoadedAction
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.ZipUpdatedAction
import ch.dreipol.multiplatform.reduxsample.shared.ui.EnterZipViewState
import org.reduxkotlin.Reducer

val enterZipViewReducer: Reducer<EnterZipViewState> = { state, action ->
    when (action) {
        is PossibleZipsLoaded -> copyAndValidate(state, state.selectedZip, action.possibleZips)
        is ZipUpdatedAction -> copyAndValidate(state, action.zip, state.possibleZips)
        is SettingsLoadedAction -> state.copy(selectedZip = action.settings.zip)
        else -> state
    }
}

private fun copyAndValidate(enterZipState: EnterZipViewState, selectedZip: Int?, possibleZips: List<Int>): EnterZipViewState {
    val invalidZip = selectedZip != null && possibleZips.contains(selectedZip).not()
    return enterZipState.copy(possibleZips = possibleZips, selectedZip = selectedZip, invalidZip = invalidZip)
}