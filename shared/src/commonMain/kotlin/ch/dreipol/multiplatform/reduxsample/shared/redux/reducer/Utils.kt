package ch.dreipol.multiplatform.reduxsample.shared.redux.reducer

import ch.dreipol.multiplatform.reduxsample.shared.database.RemindTime
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.SettingsLoadedAction
import ch.dreipol.multiplatform.reduxsample.shared.ui.DisposalCalendarEntry

internal fun isNotificationEnabled(settingsLoadedAction: SettingsLoadedAction): Boolean {
    return settingsLoadedAction.notificationSettings.isEmpty().not()
}

internal fun buildRemindTimes(settingsLoadedAction: SettingsLoadedAction): List<Pair<RemindTime, Boolean>> {
    val remindTime = settingsLoadedAction.notificationSettings.firstOrNull()?.remindTime ?: settingsLoadedAction.settings.defaultRemindTime
    return RemindTime.values().map { if (remindTime == it) it to true else it to false }
}

internal fun getNextDisposals(disposals: List<DisposalCalendarEntry>?): List<DisposalCalendarEntry> {
    if (disposals.isNullOrEmpty()) {
        return emptyList()
    }
    return disposals.filter { it.disposal.date == disposals.firstOrNull()?.disposal?.date }
}