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
        DisposalDataStore().setNewDisposals(disposals.mapNotNull { it.toDisposal(disposalType) })
    }
}