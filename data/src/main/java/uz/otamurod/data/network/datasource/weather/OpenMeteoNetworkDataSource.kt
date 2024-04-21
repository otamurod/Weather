package uz.otamurod.data.network.datasource.weather

import retrofit2.Response
import uz.otamurod.data.network.api.weather.OpenMeteoApiService
import uz.otamurod.data.network.entities.weather.ForecastResponse
import javax.inject.Inject

class OpenMeteoNetworkDataSource @Inject constructor(
    private val openMeteoApiService: OpenMeteoApiService
) {
    suspend fun getForecast(
        latitude: Double,
        longitude: Double
    ): Response<ForecastResponse> {
        return openMeteoApiService.getForecast(latitude = latitude, longitude = longitude)
    }
}