package ch.dreipol.multiplatform.reduxsample.shared.redux.thunk

import ch.dreipol.dreimultiplatform.defaultDispatcher
import ch.dreipol.dreimultiplatform.kermit
import ch.dreipol.dreimultiplatform.reduxkotlin.permissions.NotificationPermission
import ch.dreipol.multiplatform.reduxsample.shared.database.*
import ch.dreipol.multiplatform.reduxsample.shared.delight.Settings
import ch.dreipol.multiplatform.reduxsample.shared.network.ServiceFactory
import ch.dreipol.multiplatform.reduxsample.shared.redux.AppState
import ch.dreipol.multiplatform.reduxsample.shared.redux.OnboardingScreen
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.*
import ch.dreipol.multiplatform.reduxsample.shared.ui.DisposalCalendarEntry
import ch.dreipol.multiplatform.reduxsample.shared.ui.DisposalCalendarMonth
import ch.dreipol.multiplatform.reduxsample.shared.utils.AppLanguage
import ch.dreipol.multiplatform.reduxsample.shared.utils.SettingsHelper
import ch.dreipol.multiplatform.reduxsample.shared.utils.fromSettingsOrDefault
import ch.dreipol.multiplatform.reduxsample.shared.utils.now
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
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

fun loadCollectionPointsThunk(): Thunk<AppState> = { dispatch, _, _ ->
    executeNetworkOrDbAction {
        val collectionPoints = CollectionPointDataStore().findAll()
        dispatch(CollectionPointsLoadedAction(collectionPoints))
    }
}

fun calculateNextReminderThunk(): Thunk<AppState> = { dispatch, getState, _ ->
    val settings = getState.invoke().settingsState.state
    val zip = settings?.settings?.zip
    val notification = settings?.notificationSettings?.firstOrNull()
    if (zip == null || notification == null) {
        dispatch(NextRemindersCalculated(emptyList()))
    } else {
        executeNetworkOrDbAction {
            val reminders = notification.getNextReminders(zip)
            val additionalReminders = AdditionalReminderDataStore().getFutureReminders(LocalDateTime.now())
            dispatch(NextRemindersCalculated(additionalReminders + reminders))
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
        dispatch(calculateNextReminderThunk())
    }
}

fun loadDisposalsThunk(): Thunk<AppState> = { dispatch, getState, _ ->
    val state = getState.invoke()
    val settingsState = state.settingsState.state
    val zip = settingsState?.settings?.zip
    val disposalTypes = settingsState?.settings?.showDisposalTypes ?: emptyList()
    val notificationSettings = settingsState?.notificationSettings ?: emptyList()
    val notificationAreEnabled = settingsState?.systemPermission != NotificationPermission.DENIED
    if (state.calendarViewState.disposalsState.loaded.not()) {
        dispatch(syncDisposalsThunk())
    }
    if (zip == null) {
        dispatch(DisposalsLoadedAction(emptyList()))
    } else {
        executeNetworkOrDbAction {
            val disposals = DisposalDataStore().findTodayOrInFuture(zip, disposalTypes)
                .groupBy { Pair(it.date.month, it.date.year) }
                .map {
                    val disposalCalendarEntries = it.value.map { disposal ->
                        val showNotification = notificationAreEnabled && notificationSettings.any { notification ->
                            notification.disposalTypes.contains(disposal.disposalType)
                        }
                        DisposalCalendarEntry(
                            disposal, showNotification
                        )
                    }.sortedBy { disposal -> disposal.disposal.date }
                    DisposalCalendarMonth(it.key, disposalCalendarEntries)
                }.sortedWith(compareBy({ it.monthYear.second }, { it.monthYear.first.number }))
            dispatch(DisposalsLoadedAction(disposals))
        }
    }
}

fun initialNavigationThunk(): Thunk<AppState> = { dispatch, getState, _ ->
    val isOnboarding = getState().navigationState.currentScreen is OnboardingScreen
    if (isOnboarding) {
        dispatch(NavigationAction.ONBOARDING_START)
    } else {
        dispatch(NavigationAction.CALENDAR)
    }
    dispatch(loadPossibleZipsThunk())
}

fun initSettingsThunk(): Thunk<AppState> = { dispatch, _, _ ->
    executeNetworkOrDbAction {
        val settingsDataStore = SettingsDataStore()
        val settings = settingsDataStore.getSettings()
        val notificationSettings = settingsDataStore.getNotificationSettings()
        val notificationPermission = NotificationPermission.fromSettingsOrDefault()
        val reminders = settings?.let { notificationSettings.firstOrNull()?.getNextReminders(settings.zip) } ?: emptyList()
        dispatch(SettingsInitializedAction(settings, notificationPermission, notificationSettings, reminders))
        if (settings != null) {
            dispatch(SettingsLoadedAction(settings, notificationSettings, notificationPermission))
            dispatch(loadDisposalsThunk())
        }
    }
}

fun loadSavedSettingsThunk(): Thunk<AppState> = { dispatch, _, _ ->
    executeNetworkOrDbAction {
        val settingsDataStore = SettingsDataStore()
        val settings = settingsDataStore.getSettings()
        val notificationSettings = settingsDataStore.getNotificationSettings()
        val notificationPermission = NotificationPermission.fromSettingsOrDefault()
        if (settings != null) {
            dispatch(SettingsLoadedAction(settings, notificationSettings, notificationPermission))
            dispatch(loadDisposalsThunk())
            dispatch(calculateNextReminderThunk())
        }
    }
}

fun setNewZipThunk(zip: Int): Thunk<AppState> = { dispatch, getState, _ ->
    val settingsState = getState.invoke().settingsState.state
    val showDisposalTypes = settingsState?.settings?.showDisposalTypes ?: SettingsDataStore.defaultShownDisposalTypes
    val defaultRemindTime = settingsState?.settings?.defaultRemindTime ?: SettingsDataStore.defaultRemindTime
    executeNetworkOrDbAction {
        saveSettings(Settings(SettingsDataStore.UNDEFINED_ID, zip, showDisposalTypes, defaultRemindTime))
        dispatch(loadSavedSettingsThunk())
    }
}

fun updateShowDisposalType(disposalType: DisposalType, show: Boolean): Thunk<AppState> = { dispatch, getState, _ ->
    val settingsState = getState.invoke().settingsState.state
    val settings = settingsState?.settings ?: Settings(
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
    SettingsHelper.setShowOnboarding(false)
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
    { dispatch, _, _ ->
        if (AppLanguage.fromSettingsOrDefault() != appLanguage) {
            executeNetworkOrDbAction {
                SettingsHelper.setLanguage(appLanguage.shortName)
                dispatch(AppLanguageUpdated(appLanguage))
                platformSpecificAction.invoke()
            }
        }
    }

private fun saveSettings(settings: Settings) {
    var settingsToSave = settings
    val settingsDataStore = SettingsDataStore()
    settingsDataStore.getSettings()?.let { settingsToSave = settings.copy(id = it.id) }
    settingsDataStore.insertOrUpdate(settingsToSave)
}