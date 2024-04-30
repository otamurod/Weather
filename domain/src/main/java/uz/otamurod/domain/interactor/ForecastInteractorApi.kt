package uz.otamurod.domain.interactor

import kotlinx.coroutines.flow.Flow
import uz.otamurod.domain.api.model.weather.Forecast
import uz.otamurod.domain.util.DataState

interface ForecastInteractorApi {
    suspend fun getForecast(
        latitude: Double,
        longitude: Double,
        shouldUpdateLastLocation: Boolean
    ): Flow<DataState<Forecast>>

    suspend fun deleteForecast(
        latitude: Double,
        longitude: Double
    )
}