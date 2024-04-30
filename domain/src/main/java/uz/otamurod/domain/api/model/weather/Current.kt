package uz.otamurod.domain.api.model.weather

import java.io.Serializable

data class Current(
    val time: String,

    val interval: Int,

    val temperature2m: Double,

    val apparentTemperature: Double,

    val isDay: Int,

    val weatherCode: Int
): Serializable