/*
 * Copyright (c) 2021 dreipol GmbH
 *
 * Licensed under a Creative Commons Attribution-NonCommercial 4.0 International License. A copy of the license is provided with this code.
 *
 * No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
 */

package ch.dreipol.multiplatform.reduxsample.shared.redux.actions

import ch.dreipol.multiplatform.reduxsample.shared.database.DisposalType
import ch.dreipol.multiplatform.reduxsample.shared.database.RemindTime

data class UpdateShowDisposalType(val disposalType: DisposalType, val show: Boolean) : Action

//
data class UpdateAddNotification(val addNotification: Boolean) : Action

data class UpdateRemindTime(val remindTime: RemindTime) : Action