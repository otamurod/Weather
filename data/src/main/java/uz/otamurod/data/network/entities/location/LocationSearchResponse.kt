package uz.otamurod.data.network.entities.location

import com.google.gson.annotations.SerializedName

data class LocationSearchResponse(
    @SerializedName("results")
    val places: List<PlaceResponse>,

    @SerializedName("generationtime_ms")
    val generationtimeMs: Double
)