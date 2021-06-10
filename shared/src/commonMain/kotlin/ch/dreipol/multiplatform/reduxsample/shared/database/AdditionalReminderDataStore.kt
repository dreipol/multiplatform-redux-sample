/*
 * Copyright (c) 2021 dreipol GmbH
 *
 * Licensed under a Creative Commons Attribution-NonCommercial 4.0 International License. A copy of the license is provided with this code.
 *
 * No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
 */

package ch.dreipol.multiplatform.reduxsample.shared.database

import kotlinx.datetime.LocalDateTime

class AdditionalReminderDataStore {
    fun getFutureReminders(minDate: LocalDateTime): List<Reminder> {
        val database = DatabaseHelper.database
        val additionalReminders = database.additionalReminderQueries.findAfter(minDate).executeAsList()
        val disposalIds = additionalReminders.map { it.disposalId }
        val disposals = database.disposalQueries.getMany(disposalIds).executeAsList().associateBy { it.id }
        return additionalReminders.mapNotNull {
            disposals[it.disposalId]?.let { disposal ->
                Reminder(it.date, listOf(disposal))
            }
        }
    }

    fun create(disposalId: String, dateTime: LocalDateTime) {
        DatabaseHelper.database.additionalReminderQueries.insert(dateTime, disposalId)
    }
}