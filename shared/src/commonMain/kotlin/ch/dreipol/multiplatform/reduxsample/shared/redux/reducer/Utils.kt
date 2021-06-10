/*
 * Copyright (c) 2021 dreipol GmbH
 *
 * Licensed under a Creative Commons Attribution-NonCommercial 4.0 International License. A copy of the license is provided with this code.
 *
 * No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
 */

package ch.dreipol.multiplatform.reduxsample.shared.redux.reducer

import ch.dreipol.dreimultiplatform.reduxkotlin.permissions.NotificationPermission
import ch.dreipol.multiplatform.reduxsample.shared.database.RemindTime
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.SettingsLoadedAction
import ch.dreipol.multiplatform.reduxsample.shared.ui.DisposalCalendarEntry

internal fun isNotificationEnabled(action: SettingsLoadedAction): Boolean {
    return action.notificationPermission != NotificationPermission.DENIED && action.notificationSettings.isEmpty().not()
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