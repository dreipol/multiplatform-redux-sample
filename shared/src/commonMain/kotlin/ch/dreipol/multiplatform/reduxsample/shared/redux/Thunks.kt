package ch.dreipol.multiplatform.reduxsample.shared.redux

import ch.dreipol.dreimultiplatform.defaultDispatcher
import ch.dreipol.multiplatform.reduxsample.shared.database.DisposalDataStore
import ch.dreipol.multiplatform.reduxsample.shared.database.DisposalType
import ch.dreipol.multiplatform.reduxsample.shared.database.RemindTime
import ch.dreipol.multiplatform.reduxsample.shared.database.SettingsDataStore
import ch.dreipol.multiplatform.reduxsample.shared.delight.NotificationSettings
import ch.dreipol.multiplatform.reduxsample.shared.delight.Settings
import ch.dreipol.multiplatform.reduxsample.shared.network.ServiceFactory
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.DisposalsLoadedAction
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.NavigationAction
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.PossibleZipsLoaded
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.SettingsLoadedAction
import ch.dreipol.multiplatform.reduxsample.shared.ui.DisposalNotification
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.reduxkotlin.Thunk

val networkAndDbScope = CoroutineScope(defaultDispatcher)

private fun executeNetworkOrDbAction(action: suspend () -> Unit) {
    networkAndDbScope.launch {
        try {
            action.invoke()
        } catch (throwable: Throwable) {
            // TODO error handling
            throwable.printStackTrace()
        }
    }
}

fun initialNavigationThunk(): Thunk<AppState> = { dispatch, _, _ ->
    executeNetworkOrDbAction {
        val settings = SettingsDataStore().getSettings()
        if (settings == null) {
            dispatch(NavigationAction.ONBOARDING_START)
        } else {
            dispatch(NavigationAction.DASHBOARD)
        }
    }
}

fun syncDisposalsThunk(): Thunk<AppState> = { dispatch, _, _ ->
    executeNetworkOrDbAction {
        DisposalType.values().forEach {
            ServiceFactory.disposalService().syncDisposals(it)
        }
        dispatch(loadDisposalsThunk())
        dispatch(loadPossibleZipsThunk())
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
        executeNetworkOrDbAction {
            val disposals = DisposalDataStore().findTodayOrInFuture(zip, disposalTypes).map {
                DisposalNotification(it, notificationSettings.any { notification -> notification.disposalTypes.contains(it.disposalType) })
            }
            dispatch(DisposalsLoadedAction(disposals))
        }
    }
}

fun loadSavedSettingsThunk(): Thunk<AppState> = { dispatch, _, _ ->
    executeNetworkOrDbAction {
        val settingsDataStore = SettingsDataStore()
        val settings = settingsDataStore.getSettings()
        val notificationSettings = settingsDataStore.getNotificationSettings()
        if (settings != null) {
            dispatch(SettingsLoadedAction(settings, notificationSettings))
        }
    }
}

fun addOrRemoveNotificationThunk(disposalType: DisposalType): Thunk<AppState> = { dispatch, getState, _ ->
    val settingsState = getState.invoke().settingsState
    val notificationSettings = settingsState.notificationSettings
    val settings = settingsState.settings
    val notification = if (notificationSettings == null || notificationSettings.isEmpty()) {
        createNotification(emptyList(), settings?.defaultRemindTime ?: SettingsDataStore.defaultRemindTime)
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
    executeNetworkOrDbAction {
        SettingsDataStore().insertOrUpdate(updatedNotification)
        dispatch(loadSavedSettingsThunk())
    }
}

fun setNewZipThunk(zip: Int): Thunk<AppState> = { dispatch, getState, _ ->
    val settingsState = getState.invoke().settingsState
    val notificationSettings = settingsState.notificationSettings?.firstOrNull()
    val showDisposalTypes = settingsState.settings?.showDisposalTypes ?: SettingsDataStore.defaultShownDisposalTypes
    val defaultRemindTime = settingsState.settings?.defaultRemindTime ?: SettingsDataStore.defaultRemindTime
    executeNetworkOrDbAction {
        saveSettingsAndNotification(
            Settings(SettingsDataStore.UNDEFINED_ID, zip, showDisposalTypes, defaultRemindTime),
            notificationSettings
        )
        dispatch(loadSavedSettingsThunk())
    }
}

fun updateShowDisposalType(disposalType: DisposalType, show: Boolean): Thunk<AppState> = { dispatch, getState, _ ->
    val settingsState = getState.invoke().settingsState
    val settings = settingsState.settings ?: Settings(
        SettingsDataStore.UNDEFINED_ID, 0, SettingsDataStore.defaultShownDisposalTypes,
        SettingsDataStore.defaultRemindTime
    )
    val notificationSettings = settingsState.notificationSettings?.firstOrNull()
    val showDisposalTypes = settings.showDisposalTypes.toMutableSet()
    if (show) {
        showDisposalTypes.add(disposalType)
    } else {
        showDisposalTypes.remove(disposalType)
    }
    executeNetworkOrDbAction {
        saveSettingsAndNotification(settings.copy(showDisposalTypes = showDisposalTypes.toList()), notificationSettings)
        dispatch(loadSavedSettingsThunk())
    }
}

fun saveOnboardingThunk(): Thunk<AppState> = { dispatch, getState, _ ->
    val onboardingViewState = getState.invoke().onboardingViewState
    val selectedZip = onboardingViewState.enterZipState.enterZipViewState.selectedZip ?: throw IllegalStateException()
    val selectedDisposalTypes = onboardingViewState.selectDisposalTypesState
    val addNotification = onboardingViewState.addNotificationState
    val selectedRemindTime = addNotification.remindTimes.first { it.second }.first
    val settings =
        Settings(SettingsDataStore.UNDEFINED_ID, selectedZip, selectedDisposalTypes.selectedDisposalTypes, selectedRemindTime)
    val shouldAddNotification = addNotification.addNotification
    val notification = if (shouldAddNotification) {
        createNotification(DisposalType.values().toList(), settings.defaultRemindTime)
    } else {
        null
    }
    executeNetworkOrDbAction {
        saveSettingsAndNotification(settings, notification)
        dispatch(loadSavedSettingsThunk())
    }
}

fun loadPossibleZipsThunk(): Thunk<AppState> = { dispatch, _, _ ->
    executeNetworkOrDbAction {
        val zips = DisposalDataStore().getAllZips()
        dispatch(PossibleZipsLoaded(zips))
    }
}

private fun createNotification(disposalTypes: List<DisposalType>, remindTime: RemindTime): NotificationSettings {
    return NotificationSettings(SettingsDataStore.UNDEFINED_ID, disposalTypes, remindTime)
}

private fun saveSettingsAndNotification(settings: Settings, notificationSettings: NotificationSettings?) {
    var settings = settings
    val settingsDataStore = SettingsDataStore()
    settingsDataStore.getSettings()?.let { settings = settings.copy(id = it.id) }
    settingsDataStore.insertOrUpdate(settings)
    settingsDataStore.deleteNotificationSettings()
    notificationSettings?.let { settingsDataStore.insertOrUpdate(it) }
}