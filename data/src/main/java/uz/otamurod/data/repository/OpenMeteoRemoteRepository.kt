package uz.otamurod.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import uz.otamurod.data.network.datasource.weather.OpenMeteoNetworkDataSource
import uz.otamurod.data.network.mapper.weather.ForecastResponseMapper
import uz.otamurod.domain.api.model.weather.Forecast
import uz.otamurod.domain.api.repository.OpenMeteoRemoteRepositoryApi
import uz.otamurod.domain.util.DataState

class OpenMeteoRemoteRepository(
    private val openMeteoNetworkDataSource: OpenMeteoNetworkDataSource
) : OpenMeteoRemoteRepositoryApi {
    override suspend fun getForecast(
        latitude: Double, longitude: Double
    ): Flow<DataState<Forecast>> = flow {
        try {
            emit(DataState.Loading)

            val response = openMeteoNetworkDataSource.getForecast(latitude, longitude)
            if (response.isSuccessful) {
                val dataState = response.body()
                if (dataState != null) {
                    val forecastResponse = dataState.data
                    forecastResponse?.let {
                        val forecast = ForecastResponseMapper.Forecast(it).invoke()
                        emit(DataState.Success(forecast))
                    }
                }
            } else {
                emit(DataState.Error(response.message()))
            }
        } catch (e: Exception) {
            emit(DataState.Error(e.message.toString()))
        }
    }
}