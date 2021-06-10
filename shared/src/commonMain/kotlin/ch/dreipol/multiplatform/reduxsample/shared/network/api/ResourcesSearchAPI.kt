/*
 * Copyright (c) 2021 dreipol GmbH
 *
 * Licensed under a Creative Commons Attribution-NonCommercial 4.0 International License. A copy of the license is provided with this code.
 *
 * No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
 */

package ch.dreipol.multiplatform.reduxsample.shared.network.api

import ch.dreipol.multiplatform.reduxsample.shared.network.ServiceFactory
import ch.dreipol.multiplatform.reduxsample.shared.network.dtos.DataZurichResponse
import ch.dreipol.multiplatform.reduxsample.shared.network.dtos.PackageSearchResult
import ch.dreipol.multiplatform.reduxsample.shared.network.dtos.Resource
import io.ktor.client.request.*

class ResourcesSearchAPI {

    suspend fun getResources(packageId: String): List<Resource> {
        val response: DataZurichResponse<PackageSearchResult> = ServiceFactory.client().get {
            url {
                path(Endpoints.PACKAGE_SEARCH)
            }
            parameter("q", "id:$packageId")
        }
        val packages = response.result.results
        if (packages.isNullOrEmpty()) {
            throw IllegalArgumentException("Package id $packageId is invalid")
        }
        if (packages.size > 1) {
            throw IllegalArgumentException("Package id $packageId is not unique")
        }
        return packages.first().resources
    }
}