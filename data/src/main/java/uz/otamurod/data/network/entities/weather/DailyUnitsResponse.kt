package uz.otamurod.data.network.entities.weather

import com.google.gson.annotations.SerializedName

data class DailyUnitsResponse(

    @SerializedName("time")
    val time: String,

    @SerializedName("weather_code")
    val weatherCode: String,

    @SerializedName("temperature_2m_max")
    val temperature2mMax: String,

    @SerializedName("temperature_2m_min")
    val temperature2mMin: String,

    @SerializedName("sunrise")
    val sunrise: String,

    @SerializedName("sunset")
    val sunset: String,

    @SerializedName("rain_sum")
    val rainSum: String,

    @SerializedName("precipitation_probability_max")
    val precipitationProbabilityMax: String,

    @SerializedName("wind_speed_10m_max")
    val windSpeed10mMax: String
)