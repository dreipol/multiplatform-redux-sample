package ch.dreipol.multiplatform.reduxsample.shared.network

import ch.dreipol.multiplatform.reduxsample.shared.database.DisposalType
import ch.dreipol.multiplatform.reduxsample.shared.network.dtos.DataZurichResponse
import ch.dreipol.multiplatform.reduxsample.shared.network.dtos.DisposalDTO
import io.ktor.client.request.*

class DisposalAPI {

    suspend fun getDisposals(disposalType: DisposalType): List<DisposalDTO> {
        val response: DataZurichResponse<DisposalDTO> = ServiceFactory.client().get {
            // TODO set correct resourceId from disposalType
            parameter("resource_id", "6d28096a-1e04-43ef-8d18-0ce9464a7329")
        }
        return response.result.records
    }
}