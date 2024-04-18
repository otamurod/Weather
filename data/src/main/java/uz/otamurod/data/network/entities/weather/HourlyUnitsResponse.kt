package uz.otamurod.data.network.entities.weather

import com.google.gson.annotations.SerializedName

data class HourlyUnitsResponse(
    @SerializedName("time")
    val time: String,

    @SerializedName("temperature_2m")
    val temperature2m: String,

    @SerializedName("apparent_temperature")
    val apparentTemperature: String,

    @SerializedName("precipitation_probability")
    val precipitationProbability: String,

    @SerializedName("weather_code")
    val weatherCode: String
)