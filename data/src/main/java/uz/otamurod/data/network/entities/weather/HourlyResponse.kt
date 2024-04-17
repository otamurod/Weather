package uz.otamurod.data.network.entities.weather

import com.google.gson.annotations.SerializedName

data class HourlyResponse(
    @SerializedName("time")
    val time: List<String>,

    @SerializedName("temperature_2m")
    val temperature2m: List<Double>,

    @SerializedName("apparent_temperature")
    val apparentTemperature: List<Double>,

    @SerializedName("precipitation_probability")
    val precipitationProbability: List<Int>,

    @SerializedName("weather_code")
    val weatherCode: List<Int>
)