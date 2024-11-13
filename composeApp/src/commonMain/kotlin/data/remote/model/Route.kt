package data.remote.model

import debugEmulatorLocalHost
import kotlinx.serialization.Serializable

@Serializable
data class Route(
    val id: Int,
    val name: String,
    val description: String,
    val places: List<Place>,
    val distance: Double,
    val duration: Double,
    val rating: Double,
    val media: List<String>
) {
    fun getImageUrl(fileName: String?): String? =
        fileName?.let {
            "http://${debugEmulatorLocalHost()}:8080/trips/$id/$it"
        }
}
