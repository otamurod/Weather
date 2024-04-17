package uz.otamurod.data.network.entities.weather

import com.google.gson.annotations.SerializedName

data class CurrentUnitsResponse(
    @SerializedName("time")
    val time: String,

    @SerializedName("interval")
    val interval: String,

    @SerializedName("temperature_2m")
    val temperature2m: String,

    @SerializedName("apparent_temperature")
    val apparentTemperature: String,

    @SerializedName("is_day")
    val isDay: String,

    @SerializedName("weather_code")
    val weatherCode: String
)