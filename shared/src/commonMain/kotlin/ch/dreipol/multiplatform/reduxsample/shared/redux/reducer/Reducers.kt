package ch.dreipol.multiplatform.reduxsample.shared.redux.reducer

import ch.dreipol.multiplatform.reduxsample.shared.database.RemindTime
import ch.dreipol.multiplatform.reduxsample.shared.redux.AppState
import ch.dreipol.multiplatform.reduxsample.shared.redux.SettingsState
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.*
import ch.dreipol.multiplatform.reduxsample.shared.ui.*
import org.reduxkotlin.Reducer

val rootReducer: Reducer<AppState> = { state, action ->
    val navigationState = navigationReducer(state.navigationState, action)
    val settingsState = settingsReducer(state.settingsState, action)
    val calendarViewState = calendarViewReducer(state.calendarViewState, action)
    val settingsViewState = settingsViewReducer(state.settingsViewState, action)
    val onboardingViewState = onboardingViewReducer(state.onboardingViewState, action)
    state.copy(
        navigationState = navigationState, settingsState = settingsState, calendarViewState = calendarViewState,
        settingsViewState = settingsViewState, onboardingViewState = onboardingViewState
    )
}

val calendarViewReducer: Reducer<CalendarViewState> = { state, action ->
    when (action) {
        is DisposalsLoadedAction -> {
            val disposals = action.disposals.toMutableList()
            val firstMonth = disposals.firstOrNull()
            val nextDisposals = getNextDisposals(firstMonth?.disposalCalendarEntries)
            firstMonth?.let {
                val disposalsWithoutNext = it.disposalCalendarEntries.toMutableList()
                disposalsWithoutNext.removeAll(nextDisposals)
                if (disposalsWithoutNext.isEmpty()) {
                    disposals.remove(it)
                } else {
                    disposals.set(disposals.indexOf(it), it.copy(disposalCalendarEntries = disposalsWithoutNext))
                }
            }
            state.copy(disposalsState = state.disposalsState.copy(nextDisposals = nextDisposals, disposals = disposals, loaded = true))
        }
        is SettingsLoadedAction -> {
            val notificationIsTurnedOn = action.notificationSettings.isEmpty().not()
            state.copy(zip = action.settings.zip)
        }
        else -> state
    }
}

val settingsViewReducer: Reducer<SettingsViewState> = { state, action ->
    val newState = when (action) {
        is SettingsLoadedAction -> state.copy(
            calendarSettingsViewState = state.calendarSettingsViewState.copy(selectedDisposalTypes = action.settings.showDisposalTypes)
        )
        else -> state
    }
    val enterZipViewState = enterZipViewReducer(newState.zipSettingsViewState.enterZipViewState, action)
    val notificationSettingsViewState = notificationSettingsViewReducer(newState.notificationSettingsViewState, action)
    newState.copy(
        zipSettingsViewState = newState.zipSettingsViewState.copy(enterZipViewState = enterZipViewState),
        notificationSettingsViewState = notificationSettingsViewState
    )
}

val onboardingViewReducer: Reducer<OnboardingViewState> = { state, action ->
    val newState = when (action) {
        is SettingsLoadedAction ->
            state.copy(
                selectDisposalTypesState = SelectDisposalTypesState.fromSettings(action.settings),
                addNotificationState = state.addNotificationState.copy(
                    addNotification = isNotificationEnabled(action),
                    remindTimes = buildRemindTimes(action)
                )
            )
        is UpdateShowDisposalType -> state.copy(
            selectDisposalTypesState = SelectDisposalTypesState.update(state.selectDisposalTypesState, action.disposalType, action.show)
        )
        is UpdateAddNotification -> state.copy(
            addNotificationState = state.addNotificationState.copy(addNotification = action.addNotification)
        )
        is UpdateRemindTime -> state.copy(
            addNotificationState = state.addNotificationState.copy(
                remindTimes = RemindTime.values().map { if (it == action.remindTime) it to true else it to false }
            )
        )
        else -> state
    }
    val enterZipViewState = enterZipViewReducer(newState.enterZipState.enterZipViewState, action)
    newState.copy(enterZipState = newState.enterZipState.copy(enterZipViewState = enterZipViewState))
}

val settingsReducer: Reducer<SettingsState> = { state, action ->
    when (action) {
        is SettingsLoadedAction -> state.copy(settings = action.settings, notificationSettings = action.notificationSettings)
        is NextReminderCalculated -> state.copy(nextReminder = action.nextReminder)
        is AppLanguageUpdated -> state.copy(appLanguage = action.appLanguage)
        else -> state
    }
}

internal fun isNotificationEnabled(settingsLoadedAction: SettingsLoadedAction): Boolean {
    return settingsLoadedAction.notificationSettings.isEmpty().not()
}

internal fun buildRemindTimes(settingsLoadedAction: SettingsLoadedAction): List<Pair<RemindTime, Boolean>> {
    val remindTime = settingsLoadedAction.notificationSettings.firstOrNull()?.remindTime ?: settingsLoadedAction.settings.defaultRemindTime
    return RemindTime.values().map { if (remindTime == it) it to true else it to false }
}

private fun getNextDisposals(disposals: List<DisposalCalendarEntry>?): List<DisposalCalendarEntry> {
    if (disposals.isNullOrEmpty()) {
        return emptyList()
    }
    return disposals.filter { it.disposal.date == disposals.firstOrNull()?.disposal?.date }
}