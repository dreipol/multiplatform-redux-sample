package ch.dreipol.multiplatform.reduxsample.shared.redux.reducer

import ch.dreipol.multiplatform.reduxsample.shared.database.RemindTime
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.*
import ch.dreipol.multiplatform.reduxsample.shared.ui.CalendarViewState
import ch.dreipol.multiplatform.reduxsample.shared.ui.OnboardingViewState
import ch.dreipol.multiplatform.reduxsample.shared.ui.SelectDisposalTypesState
import ch.dreipol.multiplatform.reduxsample.shared.ui.SettingsViewState
import org.reduxkotlin.Reducer

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
    val languageSettingsViewState = languageSettingsViewReducer(newState.languageSettingsViewState, action)
    newState.copy(
        zipSettingsViewState = newState.zipSettingsViewState.copy(enterZipViewState = enterZipViewState),
        notificationSettingsViewState = notificationSettingsViewState,
        languageSettingsViewState = languageSettingsViewState
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
        NavigationAction.ONBOARDING_START -> state.copy(currentStep = 1)
        NavigationAction.ONBOARDING_NEXT -> state.copy(currentStep = state.currentStep + 1)
        NavigationAction.BACK -> {
            val currentStep = if (state.currentStep > 1) state.currentStep - 1 else 1
            state.copy(currentStep = currentStep)
        }
        else -> state
    }
    val enterZipViewState = enterZipViewReducer(newState.enterZipState.enterZipViewState, action)
    newState.copy(enterZipState = newState.enterZipState.copy(enterZipViewState = enterZipViewState))
}
