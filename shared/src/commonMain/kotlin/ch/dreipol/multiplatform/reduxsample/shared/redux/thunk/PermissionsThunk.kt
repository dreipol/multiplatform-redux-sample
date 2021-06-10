/*
 * Copyright (c) 2021 dreipol GmbH
 *
 * Licensed under a Creative Commons Attribution-NonCommercial 4.0 International License. A copy of the license is provided with this code.
 *
 * No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
 */

package ch.dreipol.multiplatform.reduxsample.shared.redux.thunk

import ch.dreipol.dreimultiplatform.reduxkotlin.permissions.NotificationPermission
import ch.dreipol.multiplatform.reduxsample.shared.redux.AppState
import ch.dreipol.multiplatform.reduxsample.shared.redux.actions.NotificationPermissionDidChangeAction
import ch.dreipol.multiplatform.reduxsample.shared.utils.SettingsHelper
import org.reduxkotlin.Thunk

fun didReceiveNotificationPermissionThunk(rawValue: Int): Thunk<AppState> = { dispatch, getState, _ ->
    val newPermission = NotificationPermission.fromInt(rawValue)
    val state = getState().settingsState.state
    val permission = state?.systemPermission
    if (permission != null && newPermission != permission) {
        SettingsHelper.notificationPermission = newPermission.value
        dispatch(NotificationPermissionDidChangeAction(newPermission))
        dispatch(addOrRemoveNotificationThunk())
    }
}