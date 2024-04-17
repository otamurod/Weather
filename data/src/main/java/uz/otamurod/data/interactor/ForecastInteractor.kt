package uz.otamurod.data.interactor

import kotlinx.coroutines.flow.Flow
import uz.otamurod.domain.api.model.weather.Forecast
import uz.otamurod.domain.api.repository.OpenMeteoRemoteRepositoryApi
import uz.otamurod.domain.interactor.ForecastInteractorApi
import uz.otamurod.domain.util.DataState

class ForecastInteractor(
    private val openMeteoRemoteRepositoryApi: OpenMeteoRemoteRepositoryApi
) : ForecastInteractorApi {
    override suspend fun getForecast(
        latitude: Double,
        longitude: Double
    ): Flow<DataState<Forecast>> {
        return openMeteoRemoteRepositoryApi.getForecast(latitude, longitude)
    }
}