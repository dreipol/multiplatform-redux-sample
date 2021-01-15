package ch.dreipol.multiplatform.reduxsample.shared.redux.thunk

import ch.dreipol.multiplatform.reduxsample.shared.database.DisposalType
import ch.dreipol.multiplatform.reduxsample.shared.database.RemindTime
import ch.dreipol.multiplatform.reduxsample.shared.database.SettingsDataStore
import ch.dreipol.multiplatform.reduxsample.shared.delight.NotificationSettings
import ch.dreipol.multiplatform.reduxsample.shared.redux.AppState
import org.reduxkotlin.Dispatcher
import org.reduxkotlin.Thunk

fun removeNotificationThunk(): Thunk<AppState> = { dispatch, _, _ ->
    executeNetworkOrDbAction {
        setNotificationSettings(null, dispatch)
        dispatch(loadSavedSettingsThunk())
    }
}

fun addOrRemoveNotificationThunk(): Thunk<AppState> = { dispatch, getState, _ ->
    val settingsState = getState.invoke().settingsState
    val notificationSettings = settingsState.notificationSettings
    val notification = notificationSettings?.firstOrNull()
    val defaultRemindTime = settingsState.settings?.defaultRemindTime ?: SettingsDataStore.defaultRemindTime
    executeNetworkOrDbAction {
        if (notification == null) {
            setNotificationSettings(createNotification(SettingsDataStore.defaultShownDisposalTypes, defaultRemindTime), dispatch)
        } else {
            setNotificationSettings(null, dispatch)
        }
        dispatch(loadSavedSettingsThunk())
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

fun setRemindTimeThunk(remindTime: RemindTime): Thunk<AppState> = { dispatch, getState, _ ->
    val notification = getState.invoke().settingsState.notificationSettings?.firstOrNull()
    notification?.let {
        executeNetworkOrDbAction {
            setNotificationSettings(it.copy(remindTime = remindTime), dispatch)
            dispatch(loadSavedSettingsThunk())
        }
    }
    Unit
}

expect fun checkSystemPermissionsThunk(): Thunk<AppState>

internal fun createNotification(disposalTypes: List<DisposalType>, remindTime: RemindTime): NotificationSettings {
    return NotificationSettings(SettingsDataStore.UNDEFINED_ID, disposalTypes, remindTime)
}

internal fun setNotificationSettings(notificationSettings: NotificationSettings?, dispatch: Dispatcher) {
    val settingsDataStore = SettingsDataStore()
    settingsDataStore.deleteNotificationSettings()
    notificationSettings?.let {
        settingsDataStore.insertOrUpdate(it)
        dispatch(checkSystemPermissionsThunk())
    }
}