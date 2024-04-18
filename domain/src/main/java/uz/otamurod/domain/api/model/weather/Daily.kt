package uz.otamurod.domain.api.model.weather

import java.io.Serializable

data class Daily(
    val time: List<String>,

    val weatherCode: List<Int>,

    val temperature2mMax: List<Double>,

    val temperature2mMin: List<Double>,

    val sunrise: List<String>,

    val sunset: List<String>,

    val rainSum: List<Double>,

    val precipitationProbabilityMax: List<Int>,

    val windSpeed10mMax: List<Double>
) : Serializable