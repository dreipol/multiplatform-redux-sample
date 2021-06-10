/*
 * Copyright (c) 2021 dreipol GmbH
 *
 * Licensed under a Creative Commons Attribution-NonCommercial 4.0 International License. A copy of the license is provided with this code.
 *
 * No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
 */

package ch.dreipol.multiplatform.reduxsample.shared.network.dtos

import ch.dreipol.multiplatform.reduxsample.shared.database.DisposalType
import ch.dreipol.multiplatform.reduxsample.shared.delight.Disposal
import kotlinx.datetime.toLocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DisposalDTO(
    @SerialName("_id")
    val id: Int,
    @SerialName("PLZ")
    val zip: Int?,
    @SerialName("Abholdatum")
    val disposalDate: String?
) {

    fun toDBObject(disposalType: DisposalType): Disposal? {
        if (zip == null || disposalDate == null) {
            return null
        }
        return Disposal("${disposalType.name}_$id", disposalType, zip, disposalDate.toLocalDate())
    }
}