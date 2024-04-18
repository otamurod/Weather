package uz.otamurod.domain.api.model.location

import java.io.Serializable

data class LocationSearch(
    val places: List<Place>
) : Serializable
