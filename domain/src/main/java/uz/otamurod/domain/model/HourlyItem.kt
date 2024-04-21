package uz.otamurod.domain.model

import java.io.Serializable

data class HourlyItem(
    val time: String,

    val temperature2m: Double,

    val apparentTemperature: Double,

    val precipitationProbability: Int,

    val weatherCode: Int
) : Serializable