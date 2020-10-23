package ch.dreipol.multiplatform.reduxsample.shared.redux.reducer

import ch.dreipol.multiplatform.reduxsample.shared.database.RemindTime
import ch.dreipol.multiplatform.reduxsample.shared.redux.AppState
import ch.dreipol.multiplatform.reduxsample.shared.redux.SettingsState
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.*
import ch.dreipol.multiplatform.reduxsample.shared.ui.DashboardViewState
import ch.dreipol.multiplatform.reduxsample.shared.ui.OnboardingViewState
import ch.dreipol.multiplatform.reduxsample.shared.ui.SelectDisposalTypesState
import ch.dreipol.multiplatform.reduxsample.shared.ui.SettingsViewState
import org.reduxkotlin.Reducer

val rootReducer: Reducer<AppState> = { state, action ->
    val navigationState = navigationReducer(state.navigationState, action)
    val settingsState = settingsReducer(state.settingsState, action)
    val dashboardViewState = dashboardViewReducer(state.dashboardViewState, action)
    val settingsViewState = settingsViewReducer(state.settingsViewState, action)
    val onboardingViewState = onboardingViewReducer(state.onboardingViewState, action)
    state.copy(
        navigationState = navigationState, settingsState = settingsState, dashboardViewState = dashboardViewState,
        settingsViewState = settingsViewState, onboardingViewState = onboardingViewState
    )
}

val dashboardViewReducer: Reducer<DashboardViewState> = { state, action ->
    when (action) {
        is DisposalsLoadedAction -> {
            val disposals = action.disposals.toMutableList()
            val nextDisposals = disposals.filter { it.disposal.date == disposals.firstOrNull()?.disposal?.date }
            disposals.removeAll(nextDisposals)
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
    newState.copy(zipSettingsViewState = newState.zipSettingsViewState.copy(enterZipViewState = enterZipViewState))
}

val onboardingViewReducer: Reducer<OnboardingViewState> = { state, action ->
    val newState = when (action) {
        is SettingsLoadedAction ->
            state.copy(
                selectDisposalTypesState = SelectDisposalTypesState.fromSettings(action.settings),
                addNotificationState = state.addNotificationState.copy(addNotification = action.notificationSettings.isEmpty().not())
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
        else -> state
    }
}