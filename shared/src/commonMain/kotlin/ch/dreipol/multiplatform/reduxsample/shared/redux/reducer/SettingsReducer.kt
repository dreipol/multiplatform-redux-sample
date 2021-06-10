/*
 * Copyright (c) 2021 dreipol GmbH
 *
 * Licensed under a Creative Commons Attribution-NonCommercial 4.0 International License. A copy of the license is provided with this code.
 *
 * No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
 */

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