package ch.dreipol.multiplatform.reduxsample.shared.network.dtos

import kotlinx.serialization.Serializable

@Serializable
data class DataZurichResponse<T>(
    val success: Boolean,
    val result: Result<T>,
)

@Serializable
data class Result<T>(
    val records: List<T>,
)