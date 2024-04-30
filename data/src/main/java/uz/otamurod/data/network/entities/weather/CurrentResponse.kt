package uz.otamurod.data.network.entities.weather

import com.google.gson.annotations.SerializedName

data class CurrentResponse(
    @SerializedName("time")
    val time: String,

    @SerializedName("interval")
    val interval: Int,

    @SerializedName("temperature_2m")
    val temperature2m: Double,

    @SerializedName("apparent_temperature")
    val apparentTemperature: Double,

    @SerializedName("is_day")
    val isDay: Int,

    @SerializedName("weather_code")
    val weatherCode: Int
)