/*
 * Copyright (c) 2021 dreipol GmbH
 *
 * Licensed under a Creative Commons Attribution-NonCommercial 4.0 International License. A copy of the license is provided with this code.
 *
 * No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
 */

package ch.dreipol.multiplatform.reduxsample.shared.network

import ch.dreipol.multiplatform.reduxsample.shared.database.DisposalDataStore
import ch.dreipol.multiplatform.reduxsample.shared.database.DisposalType
import ch.dreipol.multiplatform.reduxsample.shared.network.api.DisposalAPI
import ch.dreipol.multiplatform.reduxsample.shared.network.api.ResourcesSearchAPI
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class DisposalService(private val disposalAPI: DisposalAPI, private val resourcesSearchAPI: ResourcesSearchAPI) {

    suspend fun syncDisposals(disposalType: DisposalType) {
        val resources = resourcesSearchAPI.getResources(disposalType.packageId)
        val currentYear = Clock.System.now().toLocalDateTime(TimeZone.UTC).year
        val disposals = disposalAPI.getDisposals(resources, currentYear, currentYear + 1)
        DisposalDataStore().setNewDisposals(disposals.mapNotNull { it.toDBObject(disposalType) })
    }
}