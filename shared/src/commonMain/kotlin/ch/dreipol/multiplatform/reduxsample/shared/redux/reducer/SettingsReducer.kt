package ch.dreipol.multiplatform.reduxsample.shared.redux.reducer

import ch.dreipol.multiplatform.reduxsample.shared.redux.NullableState
import ch.dreipol.multiplatform.reduxsample.shared.redux.SettingsState
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.NextRemindersCalculated
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.NotificationPermissionDidChangeAction
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.SettingsInitializedAction
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.SettingsLoadedAction
import org.reduxkotlin.Reducer

val settingsReducer: Reducer<NullableState<SettingsState>> = { state, action ->
    val settingsState = state.state
    when (action) {
        is SettingsInitializedAction -> NullableState(
            SettingsState(
                action.settings,
                action.notificationPermission,
                action.notificationSettings,
                action.nextReminders
            )
        )
        is SettingsLoadedAction -> NullableState(
            settingsState!!.copy(settings = action.settings, notificationSettings = action.notificationSettings)
        )
        is NextRemindersCalculated -> NullableState(settingsState!!.copy(nextReminders = action.nextReminders))
        is NotificationPermissionDidChangeAction -> NullableState(settingsState!!.copy(systemPermission = action.value))
        else -> state
    }
}