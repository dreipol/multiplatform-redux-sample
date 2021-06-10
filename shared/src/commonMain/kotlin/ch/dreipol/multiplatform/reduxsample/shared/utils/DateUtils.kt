/*
 * Copyright (c) 2021 dreipol GmbH
 *
 * Licensed under a Creative Commons Attribution-NonCommercial 4.0 International License. A copy of the license is provided with this code.
 *
 * No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
 */

package ch.dreipol.multiplatform.reduxsample.shared.utils

import ch.dreipol.multiplatform.reduxsample.shared.delight.Disposal
import kotlinx.datetime.*

val todayEvening: LocalDateTime
    get() = createWithEveningTime(Clock.System.now().toLocalDateTime(TimeZone.UTC).date)

fun createWithEveningTime(localDate: LocalDate): LocalDateTime {
    return LocalDateTime(localDate.year, localDate.month, localDate.dayOfMonth, 18, 0, 0, 0)
}

fun formatDisposalDateForNotification(disposal: Disposal): String {
    return "${disposal.date.dayOfMonth}.${disposal.date.monthNumber}.${disposal.date.year}"
}