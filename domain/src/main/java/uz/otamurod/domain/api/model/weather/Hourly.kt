package uz.otamurod.domain.api.model.weather

import java.io.Serializable

data class Hourly(
    val time: List<String>,

    val temperature2m: List<Double>,

    val apparentTemperature: List<Double>,

    val precipitationProbability: List<Int>,

    val weatherCode: List<Int>
) : Serializable