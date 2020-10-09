package ch.dreipol.multiplatform.reduxsample.shared.redux

import ch.dreipol.dreimultiplatform.defaultDispatcher
import ch.dreipol.multiplatform.reduxsample.shared.database.DisposalDataStore
import ch.dreipol.multiplatform.reduxsample.shared.database.DisposalType
import ch.dreipol.multiplatform.reduxsample.shared.database.SettingsDataStore
import ch.dreipol.multiplatform.reduxsample.shared.delight.NotificationSettings
import ch.dreipol.multiplatform.reduxsample.shared.delight.Settings
import ch.dreipol.multiplatform.reduxsample.shared.network.ServiceFactory
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.DisposalsLoadedAction
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.SettingsLoadedAction
import ch.dreipol.multiplatform.reduxsample.shared.ui.DisposalNotification
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.reduxkotlin.Thunk

val networkAndDbScope = CoroutineScope(defaultDispatcher)

fun syncDisposalsThunk(): Thunk<AppState> = { dispatch, _, _ ->
    networkAndDbScope.launch {
        DisposalType.values().forEach {
            ServiceFactory.disposalService().syncDisposals(it)
        }
        dispatch(loadDisposalsThunk())
    }
}

fun loadDisposalsThunk(): Thunk<AppState> = { dispatch, getState, _ ->
    val settingsState = getState.invoke().settingsState
    val zip = settingsState.settings?.zip
    val disposalTypes = settingsState.settings?.showDisposalTypes ?: emptyList()
    val notificationSettings = settingsState.notificationSettings ?: emptyList()
    if (zip == null) {
        dispatch(DisposalsLoadedAction(emptyList()))
    } else {
        networkAndDbScope.launch {
            val disposals = DisposalDataStore().findTodayOrInFuture(zip, disposalTypes).map {
                DisposalNotification(it, notificationSettings.any { notification -> notification.disposalTypes.contains(it.disposalType) })
            }
            dispatch(DisposalsLoadedAction(disposals))
        }
    }
}

fun loadSavedSettingsThunk(): Thunk<AppState> = { dispatch, _, _ ->
    networkAndDbScope.launch {
        val settingsDataStore = SettingsDataStore()
        val settings = settingsDataStore.getSettings()
        val notificationSettings = settingsDataStore.getNotificationSettings()
        if (settings != null) {
            dispatch(SettingsLoadedAction(settings, notificationSettings))
        }
    }
}

fun addOrRemoveNotificationThunk(disposalType: DisposalType): Thunk<AppState> = { dispatch, getState, _ ->
    val notificationSettings = getState.invoke().settingsState.notificationSettings
    val notification = if (notificationSettings == null || notificationSettings.isEmpty()) {
        createNotification(emptyList())
    } else {
        notificationSettings.first()
    }
    val disposalTypes = notification.disposalTypes.toMutableList()
    if (disposalTypes.contains(disposalType)) {
        disposalTypes.remove(disposalType)
    } else {
        disposalTypes.add(disposalType)
    }
    val updatedNotification = notification.copy(disposalTypes = disposalTypes.toList())
    networkAndDbScope.launch {
        SettingsDataStore().insertOrUpdate(updatedNotification)
        dispatch(loadSavedSettingsThunk())
    }
}

fun setNewZipThunk(zip: Int): Thunk<AppState> = { dispatch, getState, _ ->
    val settingsState = getState.invoke().settingsState
    val notificationSettings = settingsState.notificationSettings?.first()
    val showDisposalTypes = settingsState.settings?.showDisposalTypes ?: SettingsDataStore.defaultShownDisposalTypes
    networkAndDbScope.launch {
        saveSettingsAndNotification(Settings(SettingsDataStore.UNDEFINED_ID, zip, showDisposalTypes), notificationSettings)
        dispatch(loadSavedSettingsThunk())
    }
}

fun updateShowDisposalType(disposalType: DisposalType, show: Boolean): Thunk<AppState> = { dispatch, getState, _ ->
    val settingsState = getState.invoke().settingsState
    val settings = settingsState.settings ?: Settings(SettingsDataStore.UNDEFINED_ID, 0, SettingsDataStore.defaultShownDisposalTypes)
    val notificationSettings = settingsState.notificationSettings?.first()
    val showDisposalTypes = settings.showDisposalTypes.toMutableSet()
    if (show) {
        showDisposalTypes.add(disposalType)
    } else {
        showDisposalTypes.remove(disposalType)
    }
    networkAndDbScope.launch {
        saveSettingsAndNotification(settings.copy(showDisposalTypes = showDisposalTypes.toList()), notificationSettings)
        dispatch(loadSavedSettingsThunk())
    }
}

fun saveOnboardingThunk(): Thunk<AppState> = { dispatch, getState, _ ->
    val onboardingViewState = getState.invoke().onboardingViewState
    val selectedZip = onboardingViewState.enterZipState.selectedZip ?: throw IllegalStateException()
    val selectedDisposalTypes = onboardingViewState.selectDisposalTypesState
    val showDisposalTypes = getState.invoke().settingsState.settings?.showDisposalTypes ?: SettingsDataStore.defaultShownDisposalTypes
    val settings = Settings(SettingsDataStore.UNDEFINED_ID, selectedZip, showDisposalTypes)
    val addNotification = onboardingViewState.addNotificationState.addNotification
    val notification = if (addNotification) {
        createNotification(selectedDisposalTypes.selectedDisposalTypes)
    } else {
        null
    }
    networkAndDbScope.launch {
        saveSettingsAndNotification(settings, notification)
        dispatch(loadSavedSettingsThunk())
    }
}

private fun createNotification(disposalTypes: List<DisposalType>): NotificationSettings {
    return NotificationSettings(SettingsDataStore.UNDEFINED_ID, disposalTypes, 24)
}

private fun saveSettingsAndNotification(settings: Settings, notificationSettings: NotificationSettings?) {
    var settings = settings
    val settingsDataStore = SettingsDataStore()
    settingsDataStore.getSettings()?.let { settings = settings.copy(id = it.id) }
    settingsDataStore.insertOrUpdate(settings)
    settingsDataStore.deleteNotificationSettings()
    notificationSettings?.let { settingsDataStore.insertOrUpdate(it) }
}