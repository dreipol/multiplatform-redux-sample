/*
 * Copyright (c) 2021 dreipol GmbH
 *
 * Licensed under a Creative Commons Attribution-NonCommercial 4.0 International License. A copy of the license is provided with this code.
 *
 * No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
 */

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