package ch.dreipol.multiplatform.reduxsample.shared.network

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