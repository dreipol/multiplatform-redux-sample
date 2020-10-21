package ch.dreipol.multiplatform.reduxsample.shared.redux

import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.*
import ch.dreipol.multiplatform.reduxsample.shared.redux.navigation.navigationReducer
import ch.dreipol.multiplatform.reduxsample.shared.ui.DashboardViewState
import ch.dreipol.multiplatform.reduxsample.shared.ui.OnboardingViewState
import ch.dreipol.multiplatform.reduxsample.shared.ui.SelectDisposalTypesState
import org.reduxkotlin.Reducer

val rootReducer: Reducer<AppState> = { state, action ->
    val navigationState = navigationReducer(state.navigationState, action)
    val settingsState = settingsReducer(state.settingsState, action)
    val dashboardViewState = dashboardViewReducer(state.dashboardViewState, action)
    val onboardingViewState = onboardingViewReducer(state.onboardingViewState, action)
    state.copy(
        navigationState = navigationState, settingsState = settingsState, dashboardViewState = dashboardViewState,
        onboardingViewState = onboardingViewState
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
            state.copy(
                zip = action.settings.zip,
                notificationIcon = getNotificationIcon(notificationIsTurnedOn),
                disposalTypesState = state.disposalTypesState.copy(selectedDisposalTypes = action.settings.showDisposalTypes)
            )
        }
        else -> state
    }
}

val onboardingViewReducer: Reducer<OnboardingViewState> = { state, action ->
    when (action) {
        is SettingsLoadedAction ->
            state.copy(
                enterZipState = state.enterZipState.copy(selectedZip = action.settings.zip),
                selectDisposalTypesState = SelectDisposalTypesState.fromSettings(action.settings),
                addNotificationState = state.addNotificationState.copy(addNotification = action.notificationSettings.isEmpty().not())
            )
        is ZipUpdatedAction -> state.copy(enterZipState = state.enterZipState.copy(selectedZip = action.zip))
        is UpdateNotifyDisposalType -> state.copy(
            selectDisposalTypesState = SelectDisposalTypesState.update(state.selectDisposalTypesState, action.disposalType, action.notify)
        )
        is UpdateAddNotification -> state.copy(
            addNotificationState = state.addNotificationState.copy(addNotification = action.addNotification)
        )
        else -> state
    }
}

val settingsReducer: Reducer<SettingsState> = { state, action ->
    when (action) {
        is SettingsLoadedAction -> state.copy(settings = action.settings, notificationSettings = action.notificationSettings)
        else -> state
    }
}

private fun getNotificationIcon(on: Boolean): String {
    return if (on) {
        "ic_icon_ic_24_notification_on"
    } else {
        "ic_icon_ic_24_notifications_off"
    }
}