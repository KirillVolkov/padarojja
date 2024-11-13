package data.remote.model

import debugEmulatorLocalHost
import kotlinx.serialization.Serializable

@Serializable
data class Place(
    val id: Int,
    val name: String,
    val cityId: Int,
    val address: String,
    val lat: Double,
    val lng: Double,
    val description: String,
    val openingHours: String,
    val contacts: String,
    val media: List<String> = emptyList(),
    val rating: Double
) {
    fun getImageUrl(fileName: String?): String? =
        fileName?.let {
            "http://${debugEmulatorLocalHost()}:8080/places/$id/$it"
        }
}
