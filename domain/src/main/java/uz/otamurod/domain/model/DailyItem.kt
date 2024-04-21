package uz.otamurod.domain.model

import java.io.Serializable

data class DailyItem(
    val time: String,

    val weatherCode: Int,

    val temperature2mMax: Double,

    val temperature2mMin: Double,

    val sunrise: String,

    val sunset: String,

    val rainSum: Double,

    val precipitationProbabilityMax: Int,

    val windSpeed10mMax: Double
) : Serializable