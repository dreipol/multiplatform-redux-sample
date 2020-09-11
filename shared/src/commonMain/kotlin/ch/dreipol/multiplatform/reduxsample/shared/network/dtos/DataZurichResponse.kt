package ch.dreipol.multiplatform.reduxsample.shared.network.dtos

import kotlinx.serialization.Serializable

@Serializable
data class DataZurichResponse<Result>(
    val success: Boolean,
    val result: Result,
)

@Serializable
data class DataResult<Record>(
    val records: List<Record>,
)

@Serializable
data class PackageSearchResult(
    val results: List<Package>
)

@Serializable
data class Package(
    val resources: List<Resource>
)

@Serializable
data class Resource(
    val id: String,
    val name: String
)