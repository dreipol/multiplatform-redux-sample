/*
 * Copyright (c) 2021 dreipol GmbH
 *
 * Licensed under a Creative Commons Attribution-NonCommercial 4.0 International License. A copy of the license is provided with this code.
 *
 * No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
 */

package ch.dreipol.multiplatform.reduxsample.shared.database

enum class CollectionPointType(val translationKey: String, val icon: String) {
    GLASS("collection_point_type_glass", "ic_24_glas_colored"),
    METAL("collection_point_type_metal", "ic_24_tin_colored"),
    OIL("collection_point_type_oil", "ic_24_oil_colored"),
}