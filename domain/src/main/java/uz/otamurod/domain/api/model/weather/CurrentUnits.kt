package uz.otamurod.domain.api.model.weather

import java.io.Serializable

data class CurrentUnits(
    val time: String,

    val interval: String,

    val temperature2m: String,

    val apparentTemperature: String,

    val isDay: String,

    val weatherCode: String
): Serializable