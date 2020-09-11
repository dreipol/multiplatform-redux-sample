package ch.dreipol.multiplatform.reduxsample.shared.network.dtos

import ch.dreipol.multiplatform.reduxsample.shared.database.DisposalType
import ch.dreipol.multiplatform.reduxsample.shared.delight.Disposal
import kotlinx.datetime.toLocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DisposalDTO(
    @SerialName("")
    val id: Int,
    @SerialName("PLZ")
    val zip: Int,
    @SerialName("Abholdatum")
    val disposalDate: String
) {

    fun toDisposal(disposalType: DisposalType): Disposal {
        return Disposal(id, disposalType, zip, disposalDate.toLocalDate())
    }
}