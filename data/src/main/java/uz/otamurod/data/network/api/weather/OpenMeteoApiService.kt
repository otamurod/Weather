package uz.otamurod.data.network.api.weather

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import uz.otamurod.data.network.entities.weather.ForecastResponse

interface OpenMeteoApiService {

    // https://api.open-meteo.com/v1/forecast?latitude=39.9606&longitude=68.3958&current=temperature_2m,apparent_temperature,is_day,weather_code&hourly=temperature_2m,apparent_temperature,precipitation_probability,weather_code&daily=weather_code,temperature_2m_max,temperature_2m_min,sunrise,sunset,rain_sum,precipitation_probability_max,wind_speed_10m_max&timezone=auto

    @GET("forecast")
    suspend fun getForecast(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("current") currentFields: String = "temperature_2m,apparent_temperature,is_day,weather_code",
        @Query("hourly") hourlyFields: String = "temperature_2m,apparent_temperature,precipitation_probability,weather_code",
        @Query("daily") dailyFields: String = "weather_code,temperature_2m_max,temperature_2m_min,sunrise,sunset,rain_sum,precipitation_probability_max,wind_speed_10m_max",
        @Query("timezone") timezone: String = "auto"
    ): Response<ForecastResponse>

}