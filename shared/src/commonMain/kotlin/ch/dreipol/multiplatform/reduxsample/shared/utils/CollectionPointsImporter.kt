package ch.dreipol.multiplatform.reduxsample.shared.utils

import ch.dreipol.multiplatform.reduxsample.shared.delight.CollectionPoint
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

object CollectionPointsImporter {

    fun readCollectionPoints(): List<CollectionPoint> {
        return Json {
            ignoreUnknownKeys = true
        }.decodeFromString<CollectionPointsDTO>(getAppConfiguration().fileReader.readCollectionPointsFile()).toCollectionPoints()
    }
}

@Serializable
data class CollectionPointsDTO(
    val features: List<CollectionPointFeature>,
) {
    fun toCollectionPoints(): List<CollectionPoint> {
        return features.map { it.toCollectionPoint() }
    }
}

@Serializable
data class CollectionPointFeature(
    val geometry: CollectionPointGeometry,
    val properties: CollectionPointProperties,
) {
    fun toCollectionPoint(): CollectionPoint {
        return CollectionPoint(
            properties.id, properties.address, properties.name, geometry.lat, geometry.lon, properties.zip.toInt(),
            properties.hasMetal, properties.hasGlass, properties.hasOil, properties.isWheelChairAccessible
        )
    }
}

@Serializable
data class CollectionPointGeometry(
    val coordinates: List<Double>
) {
    val lon: Double
        get() = coordinates[0]
    val lat: Double
        get() = coordinates[1]
}

@Serializable
data class CollectionPointProperties(
    @SerialName("poi_id")
    val id: String,
    @SerialName("da")
    val owner: String,
    @SerialName("adresse")
    val street: String,
    @SerialName("ort")
    val place: String,
    val name: String,
    @SerialName("plz")
    val zip: String,
    @SerialName("metall")
    val metal: String?,
    @SerialName("glas")
    val glass: String?,
    @SerialName("oel")
    val oil: String?,
    @SerialName("behindertengerecht")
    val wheelChairAccessible: String?

) {
    val hasMetal: Boolean
        get() = metal == "X"
    val hasGlass: Boolean
        get() = glass == "X"
    val hasOil: Boolean
        get() = oil == "X"
    val isWheelChairAccessible: Boolean
        get() = wheelChairAccessible == "X"
    val address: String
        get() = "$owner\n$street\n$zip $place"
}