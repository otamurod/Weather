package uz.otamurod.domain.api.model.weather

import java.io.Serializable

data class HourlyUnits(
    val time: String,

    val temperature2m: String,

    val apparentTemperature: String,

    val precipitationProbability: String,

    val weatherCode: String
) : Serializable