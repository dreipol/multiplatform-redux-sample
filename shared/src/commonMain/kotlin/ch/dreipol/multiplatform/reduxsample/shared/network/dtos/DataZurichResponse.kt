/*
 * Copyright (c) 2021 dreipol GmbH
 *
 * Licensed under a Creative Commons Attribution-NonCommercial 4.0 International License. A copy of the license is provided with this code.
 *
 * No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
 */

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