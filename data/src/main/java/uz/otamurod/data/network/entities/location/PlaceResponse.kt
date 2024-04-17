package uz.otamurod.data.network.entities.location

import com.google.gson.annotations.SerializedName

data class PlaceResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("latitude")
    val latitude: Double,

    @SerializedName("longitude")
    val longitude: Double,

    @SerializedName("elevation")
    val elevation: Double,

    @SerializedName("feature_code")
    val featureCode: String,

    @SerializedName("country_code")
    val countryCode: String,

    @SerializedName("admin1_id")
    val admin1Id: Int,

    @SerializedName("timezone")
    val timezone: String,

    @SerializedName("population")
    val population: Int,

    @SerializedName("country_id")
    val countryId: Int,

    @SerializedName("country")
    val country: String,

    @SerializedName("admin1")
    val admin1: String
)