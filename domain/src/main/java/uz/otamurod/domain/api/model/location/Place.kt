package uz.otamurod.domain.api.model.location

import java.io.Serializable

data class Place(
    val id: Int,

    val name: String,

    val latitude: Double,

    val longitude: Double,

    val elevation: Double,

    val featureCode: String,

    val countryCode: String,

    val admin1Id: Int,

    val timezone: String,

    val population: Int,

    val countryId: Int,

    val country: String,

    val admin1: String
): Serializable
