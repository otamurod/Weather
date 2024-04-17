package uz.otamurod.domain.api.model.weather

import java.io.Serializable

data class DailyUnits(
    val time: String,

    val weatherCode: String,

    val temperature2mMax: String,

    val temperature2mMin: String,

    val sunrise: String,

    val sunset: String,

    val rainSum: String,

    val precipitationProbabilityMax: String,

    val windSpeed10mMax: String
) : Serializable