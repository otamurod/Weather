package uz.otamurod.data.network.datasource.weather

import retrofit2.Response
import uz.otamurod.data.network.api.weather.OpenMeteoApiService
import uz.otamurod.data.network.entities.weather.ForecastResponse
import uz.otamurod.domain.util.DataState

class OpenMeteoNetworkDataSource(
    private val openMeteoApiService: OpenMeteoApiService
) {
    suspend fun getForecast(
        latitude: Double,
        longitude: Double
    ): Response<DataState<ForecastResponse>> {
        return openMeteoApiService.getForecast(latitude = latitude, longitude = longitude)
    }
}