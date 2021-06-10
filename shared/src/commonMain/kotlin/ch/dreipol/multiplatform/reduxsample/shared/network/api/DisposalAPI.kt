/*
 * Copyright (c) 2021 dreipol GmbH
 *
 * Licensed under a Creative Commons Attribution-NonCommercial 4.0 International License. A copy of the license is provided with this code.
 *
 * No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
 */

package ch.dreipol.multiplatform.reduxsample.shared.network.api

import ch.dreipol.multiplatform.reduxsample.shared.network.ServiceFactory
import ch.dreipol.multiplatform.reduxsample.shared.network.dtos.DataResult
import ch.dreipol.multiplatform.reduxsample.shared.network.dtos.DataZurichResponse
import ch.dreipol.multiplatform.reduxsample.shared.network.dtos.DisposalDTO
import ch.dreipol.multiplatform.reduxsample.shared.network.dtos.Resource
import io.ktor.client.request.*

class DisposalAPI {

    suspend fun getDisposals(resources: List<Resource>, vararg years: Int): List<DisposalDTO> {
        if (resources.isEmpty() || years.isEmpty()) {
            return emptyList()
        }
        val client = ServiceFactory.client()
        val result = mutableListOf<DisposalDTO>()
        years.forEach { year ->
            val resource = resources.find { it.name.contains(year.toString()) } ?: return@forEach
            val response: DataZurichResponse<DataResult<DisposalDTO>> = client.get {
                url {
                    path(Endpoints.DATA_SEARCH)
                }
                parameter("resource_id", resource.id)
                parameter("limit", Long.MAX_VALUE)
            }
            result.addAll(response.result.records)
        }
        return result
    }
}