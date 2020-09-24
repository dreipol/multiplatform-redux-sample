package ch.dreipol.multiplatform.reduxsample.shared.redux

import ch.dreipol.dreimultiplatform.defaultDispatcher
import ch.dreipol.multiplatform.reduxsample.shared.database.DisposalDataStore
import ch.dreipol.multiplatform.reduxsample.shared.database.DisposalType
import ch.dreipol.multiplatform.reduxsample.shared.database.SettingsDataStore
import ch.dreipol.multiplatform.reduxsample.shared.delight.Settings
import ch.dreipol.multiplatform.reduxsample.shared.network.ServiceFactory
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.DisposalsLoadedAction
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.SettingsLoadedAction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.reduxkotlin.Thunk

val networkAndDbScope = CoroutineScope(defaultDispatcher)

fun syncDisposalsThunk(): Thunk<AppState> = { dispatch, _, _ ->
    networkAndDbScope.launch {
        ServiceFactory.disposalService().syncDisposals(DisposalType.CARTON)
        dispatch(loadDisposalsThunk())
    }
}

fun loadDisposalsThunk(): Thunk<AppState> = { dispatch, _, _ ->
    networkAndDbScope.launch {
        dispatch(DisposalsLoadedAction(DisposalDataStore().getAllDisposals()))
    }
}

fun loadSavedSettings(): Thunk<AppState> = { dispatch, _, _ ->
    // TODO load notificationSettings
    networkAndDbScope.launch {
        val settings = SettingsDataStore().getSettings()
        if (settings != null) {
            dispatch(SettingsLoadedAction(settings, emptyList()))
        }
    }
}

fun saveOnboardingThunk(): Thunk<AppState> = { dispatch, getState, _ ->
    val onboardingViewState = getState.invoke().onboardingViewState
    val selectedZip = onboardingViewState.enterZipState.selectedZip ?: throw IllegalStateException()
    val selectedDisposalTypes = onboardingViewState.selectDisposalTypesState
    var settings = Settings(
        SettingsDataStore.UNDEFINED_ID, selectedZip, selectedDisposalTypes.showCarton, selectedDisposalTypes.showBioWaste,
        selectedDisposalTypes.showPaper, selectedDisposalTypes.showETram, selectedDisposalTypes.showCargoTram,
        selectedDisposalTypes.showTextils, selectedDisposalTypes.showHazardousWaste, selectedDisposalTypes.showSweepings
    )
    networkAndDbScope.launch {
        val settingsDataStore = SettingsDataStore()
        settingsDataStore.getSettings()?.let { settings = settings.copy(id = it.id) }
        // TODO save notificationSettings
        settingsDataStore.insertOrUpdate(settings)
    }
}