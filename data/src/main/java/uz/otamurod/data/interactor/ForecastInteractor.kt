package uz.otamurod.data.interactor

import android.util.Log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import uz.otamurod.domain.api.model.weather.Forecast
import uz.otamurod.domain.api.repository.OpenMeteoRemoteRepositoryApi
import uz.otamurod.domain.api.repository.WeatherLocalRepositoryApi
import uz.otamurod.domain.interactor.ForecastInteractorApi
import uz.otamurod.domain.util.DataState
import java.io.IOException
import javax.inject.Inject

class ForecastInteractor @Inject constructor(
    private val openMeteoRemoteRepositoryApi: OpenMeteoRemoteRepositoryApi,
    private val weatherLocalRepositoryApi: WeatherLocalRepositoryApi
) : ForecastInteractorApi {
    override suspend fun getForecast(
        latitude: Double,
        longitude: Double,
        shouldUpdateLastLocation: Boolean
    ): Flow<DataState<Forecast>> = channelFlow {
        openMeteoRemoteRepositoryApi.getForecast(latitude, longitude, shouldUpdateLastLocation)
            .retryWhen { cause, _ ->
                if (cause is IOException) {
                    send(DataState.Error(cause.message.toString()))
                    Log.d(TAG, "getForecast: IOException")
                    delay(RETRY_TIME_IN_MILLIS)
                    true
                } else {
                    false
                }
            }.catch {
                send(DataState.Error(it.message.toString()))
            }.collectLatest {
                Log.d(TAG, "getForecast: forecast = ${it.data}")
                send(DataState.Success(it.data!!))
            }
    }

    override suspend fun deleteForecast(latitude: Double, longitude: Double) {
        weatherLocalRepositoryApi.deleteForecast(latitude, longitude)
    }

    companion object {
        private const val TAG = "ForecastInteractor"
        private const val RETRY_TIME_IN_MILLIS = 15_000L
    }
}