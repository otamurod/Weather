package uz.otamurod.data.network.entities.weather

import com.google.gson.annotations.SerializedName

data class DailyResponse(

    @SerializedName("time")
    val time: List<String>,

    @SerializedName("weather_code")
    val weatherCode: List<Int>,

    @SerializedName("temperature_2m_max")
    val temperature2mMax: List<Double>,

    @SerializedName("temperature_2m_min")
    val temperature2mMin: List<Double>,

    @SerializedName("sunrise")
    val sunrise: List<String>,

    @SerializedName("sunset")
    val sunset: List<String>,

    @SerializedName("rain_sum")
    val rainSum: List<Double>,

    @SerializedName("precipitation_probability_max")
    val precipitationProbabilityMax: List<Int>,

    @SerializedName("wind_speed_10m_max")
    val windSpeed10mMax: List<Double>
)