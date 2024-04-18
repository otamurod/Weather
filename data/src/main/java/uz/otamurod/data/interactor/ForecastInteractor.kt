package uz.otamurod.data.interactor

import kotlinx.coroutines.flow.Flow
import uz.otamurod.domain.api.model.weather.Forecast
import uz.otamurod.domain.api.repository.OpenMeteoRemoteRepositoryApi
import uz.otamurod.domain.api.repository.WeatherLocalRepositoryApi
import uz.otamurod.domain.interactor.ForecastInteractorApi
import uz.otamurod.domain.util.DataState
import javax.inject.Inject

class ForecastInteractor @Inject constructor(
    private val openMeteoRemoteRepositoryApi: OpenMeteoRemoteRepositoryApi,
    private val weatherLocalRepositoryApi: WeatherLocalRepositoryApi
) : ForecastInteractorApi {
    override suspend fun getForecast(
        latitude: Double,
        longitude: Double
    ): Flow<DataState<Forecast>> {
        return openMeteoRemoteRepositoryApi.getForecast(latitude, longitude)
    }

    override suspend fun getForecastOfPlaceByLatLong(
        latitude: Double,
        longitude: Double
    ): Forecast? {
        return weatherLocalRepositoryApi.getForecastOfPlaceByLatLong(latitude, longitude)
    }

    override suspend fun insertForecastOfPlace(forecast: Forecast) {
        return weatherLocalRepositoryApi.insertForecastOfPlace(forecast)
    }
}