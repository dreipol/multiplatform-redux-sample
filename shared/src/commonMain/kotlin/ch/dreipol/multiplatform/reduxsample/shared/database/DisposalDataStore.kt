/*
 * Copyright (c) 2021 dreipol GmbH
 *
 * Licensed under a Creative Commons Attribution-NonCommercial 4.0 International License. A copy of the license is provided with this code.
 *
 * No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
 */

package ch.dreipol.multiplatform.reduxsample.shared.database

import ch.dreipol.multiplatform.reduxsample.shared.delight.Disposal
import kotlinx.datetime.*

class DisposalDataStore {

    fun setNewDisposals(disposals: List<Disposal>) {
        disposals.forEach { DatabaseHelper.database.disposalQueries.insertOrUpdate(it) }
    }

    fun findTodayOrInFuture(
        zip: Int,
        disposalTypes: List<DisposalType>,
//        Use for debugging other cases, than today
//        minDate: LocalDate = LocalDate(2020,11,1)
        minDate: LocalDate = Clock.System.now().toLocalDateTime(TimeZone.UTC).date
    ): List<Disposal> {
        return DatabaseHelper.database.disposalQueries.byZip(zip).executeAsList().filter { it.date >= minDate }
            .filter { disposalTypes.contains(it.disposalType) }
            .sortedWith(compareBy({ it.date }, { it.disposalType.ordinal }))
    }

    fun getFutureDisposals(minDate: LocalDate, zip: Int, disposalTypes: List<DisposalType>): List<Disposal> {
        return findTodayOrInFuture(zip, disposalTypes, minDate)
    }

    fun getAllZips(): List<Int> {
        return DatabaseHelper.database.disposalQueries.allZips().executeAsList()
    }
}