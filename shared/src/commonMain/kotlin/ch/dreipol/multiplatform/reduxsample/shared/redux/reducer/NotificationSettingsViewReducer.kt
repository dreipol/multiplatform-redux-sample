package ch.dreipol.multiplatform.reduxsample.shared.redux.reducer

import ch.dreipol.multiplatform.reduxsample.shared.database.DisposalType
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.SettingsLoadedAction
import ch.dreipol.multiplatform.reduxsample.shared.ui.NotificationSettingsViewState
import org.reduxkotlin.Reducer

val notificationSettingsViewReducer: Reducer<NotificationSettingsViewState> = { state, action ->
    when (action) {
        is SettingsLoadedAction ->
            state.copy(
                notificationEnabled = isNotificationEnabled(action), remindTimes = buildRemindTimes(action),
                selectedDisposalTypes = action.notificationSettings.firstOrNull()?.disposalTypes ?: DisposalType.values().toList()
            )
        else -> state
    }
}