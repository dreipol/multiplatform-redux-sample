package ch.dreipol.multiplatform.reduxsample.shared.redux.thunk

import ch.dreipol.dreimultiplatform.defaultDispatcher
import ch.dreipol.dreimultiplatform.kermit
import ch.dreipol.multiplatform.reduxsample.shared.database.*
import ch.dreipol.multiplatform.reduxsample.shared.delight.Settings
import ch.dreipol.multiplatform.reduxsample.shared.network.ServiceFactory
import ch.dreipol.multiplatform.reduxsample.shared.redux.AppState
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.*
import ch.dreipol.multiplatform.reduxsample.shared.ui.DisposalCalendarEntry
import ch.dreipol.multiplatform.reduxsample.shared.ui.DisposalCalendarMonth
import ch.dreipol.multiplatform.reduxsample.shared.utils.AppLanguage
import ch.dreipol.multiplatform.reduxsample.shared.utils.MpfSettingsHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.datetime.number
import org.reduxkotlin.Thunk

val networkAndDbScope = CoroutineScope(defaultDispatcher)

internal fun executeNetworkOrDbAction(action: suspend () -> Unit) {
    networkAndDbScope.launch {
        try {
            action.invoke()
        } catch (throwable: Throwable) {
            kermit().e(throwable) { "Thunk failed" }
        }
    }
}

fun calculateNextReminderThunk(): Thunk<AppState> = { dispatch, getState, _ ->
    val settings = getState.invoke().settingsState
    val zip = settings.settings?.zip
    val notification = settings.notificationSettings?.firstOrNull()
    if (zip == null || notification == null) {
        dispatch(NextReminderCalculated(null))
    } else {
        executeNetworkOrDbAction {
            val reminder = notification.getNextReminder(zip)
            dispatch(NextReminderCalculated(reminder))
        }
    }
}

fun initialNavigationThunk(): Thunk<AppState> = { dispatch, _, _ ->
    executeNetworkOrDbAction {
        val settings = SettingsDataStore().getSettings()
        if (settings == null) {
            dispatch(NavigationAction.ONBOARDING_START)
        } else {
            dispatch(NavigationAction.CALENDAR)
        }
    }
    dispatch(loadPossibleZipsThunk())
}

fun syncDisposalsThunk(): Thunk<AppState> = { dispatch, _, _ ->
    executeNetworkOrDbAction {
        DisposalType.values().forEach {
            ServiceFactory.disposalService().syncDisposals(it)
        }
        dispatch(loadDisposalsThunk())
        dispatch(loadPossibleZipsThunk())
        dispatch(calculateNextReminderThunk())
    }
}

fun loadDisposalsThunk(): Thunk<AppState> = { dispatch, getState, _ ->
    val state = getState.invoke()
    val settingsState = state.settingsState
    val zip = settingsState.settings?.zip
    val disposalTypes = settingsState.settings?.showDisposalTypes ?: emptyList()
    val notificationSettings = settingsState.notificationSettings ?: emptyList()
    if (state.calendarViewState.disposalsState.loaded.not()) {
        dispatch(syncDisposalsThunk())
    }
    if (zip == null) {
        dispatch(DisposalsLoadedAction(emptyList()))
    } else {
        executeNetworkOrDbAction {
            val disposals = DisposalDataStore().findTodayOrInFuture(zip, disposalTypes).groupBy { Pair(it.date.month, it.date.year) }.map {
                val disposalCalendarEntries = it.value.map { disposal ->
                    DisposalCalendarEntry(
                        disposal,
                        notificationSettings.any { notification -> notification.disposalTypes.contains(disposal.disposalType) }
                    )
                }.sortedBy { disposal -> disposal.disposal.date }
                DisposalCalendarMonth(it.key, disposalCalendarEntries)
            }.sortedWith(compareBy({ it.monthYear.second }, { it.monthYear.first.number }))
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
            dispatch(loadDisposalsThunk())
            dispatch(calculateNextReminderThunk())
        }
    }
}

fun setNewZipThunk(zip: Int): Thunk<AppState> = { dispatch, getState, _ ->
    val settingsState = getState.invoke().settingsState
    val showDisposalTypes = settingsState.settings?.showDisposalTypes ?: SettingsDataStore.defaultShownDisposalTypes
    val defaultRemindTime = settingsState.settings?.defaultRemindTime ?: SettingsDataStore.defaultRemindTime
    executeNetworkOrDbAction {
        saveSettings(Settings(SettingsDataStore.UNDEFINED_ID, zip, showDisposalTypes, defaultRemindTime))
        dispatch(loadSavedSettingsThunk())
    }
}

fun updateShowDisposalType(disposalType: DisposalType, show: Boolean): Thunk<AppState> = { dispatch, getState, _ ->
    val settingsState = getState.invoke().settingsState
    val settings = settingsState.settings ?: Settings(
        SettingsDataStore.UNDEFINED_ID, 0, SettingsDataStore.defaultShownDisposalTypes,
        SettingsDataStore.defaultRemindTime
    )
    val showDisposalTypes = settings.showDisposalTypes.toMutableSet()
    if (show) {
        showDisposalTypes.add(disposalType)
    } else {
        showDisposalTypes.remove(disposalType)
    }
    executeNetworkOrDbAction {
        saveSettings(settings.copy(showDisposalTypes = showDisposalTypes.toList()))
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
        saveSettings(settings)
        setNotificationSettings(notification, dispatch)
        dispatch(loadSavedSettingsThunk())
    }
}

fun loadPossibleZipsThunk(): Thunk<AppState> = { dispatch, _, _ ->
    executeNetworkOrDbAction {
        val zips = DisposalDataStore().getAllZips()
        dispatch(PossibleZipsLoaded(zips))
    }
}

fun setNewAppLanguageThunk(appLanguage: AppLanguage, platformSpecificAction: () -> Unit): Thunk<AppState> =
    { dispatch, getState, _ ->
        if (getState.invoke().settingsState.appLanguage != appLanguage) {
            executeNetworkOrDbAction {
                MpfSettingsHelper.setLanguage(appLanguage.shortName)
                dispatch(AppLanguageUpdated(appLanguage))
            }
            platformSpecificAction.invoke()
        }
    }

private fun saveSettings(settings: Settings) {
    var settingsToSave = settings
    val settingsDataStore = SettingsDataStore()
    settingsDataStore.getSettings()?.let { settingsToSave = settings.copy(id = it.id) }
    settingsDataStore.insertOrUpdate(settingsToSave)
}