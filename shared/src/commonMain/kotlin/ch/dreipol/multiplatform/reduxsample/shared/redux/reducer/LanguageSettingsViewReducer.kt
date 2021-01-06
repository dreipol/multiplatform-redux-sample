package ch.dreipol.multiplatform.reduxsample.shared.redux.reducer

import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.AppLanguageUpdated
import ch.dreipol.multiplatform.reduxsample.shared.ui.LanguageSettingsViewState
import org.reduxkotlin.Reducer

val languageSettingsViewReducer: Reducer<LanguageSettingsViewState> = { state, action ->
    when (action) {
        is AppLanguageUpdated -> state.copy(appLanguage = action.appLanguage)
        else -> state
    }
}