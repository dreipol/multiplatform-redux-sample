package ch.dreipol.multiplatform.reduxsample.shared.database

enum class CollectionPointType(val translationKey: String, val icon: String) {
    GLASS("collection_point_type_glass", "ic_24_glas_colored"),
    METAL("collection_point_type_metal", "ic_24_tin_colored"),
    OIL("collection_point_type_oil", "ic_24_oil_colored"),
}