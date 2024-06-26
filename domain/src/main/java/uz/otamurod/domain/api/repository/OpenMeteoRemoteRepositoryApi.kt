package uz.otamurod.domain.api.repository

import kotlinx.coroutines.flow.Flow
import uz.otamurod.domain.api.model.weather.Forecast
import uz.otamurod.domain.util.DataState

interface OpenMeteoRemoteRepositoryApi {
    suspend fun getForecast(
        latitude: Double, longitude: Double, shouldUpdateLastLocation: Boolean
    ): Flow<DataState<Forecast>>

    suspend fun fetchForecastOnline(
        latitude: Double, longitude: Double, shouldUpdateLastLocation: Boolean
    ): Flow<Forecast?>
}