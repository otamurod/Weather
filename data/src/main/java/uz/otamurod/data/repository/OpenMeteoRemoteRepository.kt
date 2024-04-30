package uz.otamurod.data.repository

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import org.joda.time.DateTime
import uz.otamurod.data.database.datasource.WeatherDatabaseDataSource
import uz.otamurod.data.database.mapper.weather.*
import uz.otamurod.data.network.datasource.weather.OpenMeteoNetworkDataSource
import uz.otamurod.data.network.mapper.weather.ForecastResponseMapper
import uz.otamurod.domain.api.model.weather.Forecast
import uz.otamurod.domain.api.repository.OpenMeteoRemoteRepositoryApi
import uz.otamurod.domain.interactor.LastLocationInteractorApi
import uz.otamurod.domain.interactor.LocationSearchInteractorApi
import uz.otamurod.domain.util.DataState
import javax.inject.Inject

class OpenMeteoRemoteRepository @Inject constructor(
    private val openMeteoNetworkDataSource: OpenMeteoNetworkDataSource,
    private val weatherDatabaseDataSource: WeatherDatabaseDataSource,
    private val lastLocationInteractorApi: LastLocationInteractorApi,
    private val locationSearchInteractorApi: LocationSearchInteractorApi
) : OpenMeteoRemoteRepositoryApi {
    override suspend fun getForecast(
        latitude: Double, longitude: Double, shouldUpdateLastLocation: Boolean
    ): Flow<DataState<Forecast>> = channelFlow {
        try {
            val forecastByLatLong =
                weatherDatabaseDataSource.getForecastByLatLong(latitude, longitude)
            Log.d(
                TAG,
                "getForecast:forecastByLatLong (latitude = ${latitude}, longitude = ${longitude}) = $forecastByLatLong"
            )
            /**
             * We need to make sure that the weather data is exist locally
             * If the weather forecast for a particular place is exist in our Database, then we can utilize it
             * Otherwise, we should fetch data from API
             */

            if (forecastByLatLong != null) {
                val currentUnitsByForecastId =
                    weatherDatabaseDataSource.getCurrentUnitsByForecastId(latitude, longitude)
                val currentByForecastId =
                    weatherDatabaseDataSource.getCurrentByForecastId(latitude, longitude)
                val hourlyUnitsByForecastId =
                    weatherDatabaseDataSource.getHourlyUnitsByForecastId(latitude, longitude)
                val hourlyByForecastId =
                    weatherDatabaseDataSource.getHourlyByForecastId(latitude, longitude)
                val dailyUnitsByForecastId =
                    weatherDatabaseDataSource.getDailyUnitsByForecastId(latitude, longitude)
                val dailyByForecastId =
                    weatherDatabaseDataSource.getDailyByForecastId(latitude, longitude)

                if (currentUnitsByForecastId != null && currentByForecastId != null && hourlyUnitsByForecastId != null && !hourlyByForecastId.isNullOrEmpty() && dailyUnitsByForecastId != null && !dailyByForecastId.isNullOrEmpty()) {
                    val forecast = ForecastDbMapper.fromDto(
                        forecastEntity = forecastByLatLong,
                        currentUnitsEntity = currentUnitsByForecastId,
                        currentEntity = currentByForecastId,
                        hourlyUnitsEntity = hourlyUnitsByForecastId,
                        hourlyEntity = hourlyByForecastId,
                        dailyUnitsEntity = dailyUnitsByForecastId,
                        dailyEntity = dailyByForecastId
                    )

                    /**
                     * We must check if we can show forecast info of time when a user launches the app
                     * If the weather forecast is outdated, then we will fetch data Online and update the local data too
                     * Otherwise, we can still use the local data
                     */
                    Log.d(TAG, "getForecast: $forecast")
                    if (forecast.daily.time.contains(DateTime.now().toString("yyyy-MM-dd"))) {
                        Log.d(TAG, "getForecast: \"Sent from Database\"")
                        send(DataState.Success(forecast))
                    } else {
                        fetchForecastOnline(
                            latitude,
                            longitude,
                            shouldUpdateLastLocation
                        ).collectLatest { forecast ->
                            if (forecast != null) {
                                Log.d(TAG, "getForecast: \"Sent from API\"")

                                send(DataState.Success(forecast))
                            } else {
                                send(DataState.Error("Could not fetch forecast: @null"))
                                Log.d(TAG, "getForecast: Could not fetch forecast: @null")
                            }
                        }
                    }
                }
            } else {
                fetchForecastOnline(
                    latitude,
                    longitude,
                    shouldUpdateLastLocation
                ).collectLatest { forecast ->
                    if (forecast != null) {
                        Log.d(TAG, "getForecast: \"Sent from API\"")

                        send(DataState.Success(forecast))
                    } else {
                        send(DataState.Error("Could not fetch forecast: @null"))
                        Log.d(TAG, "getForecast: Could not fetch forecast: @null")
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun fetchForecastOnline(
        latitude: Double, longitude: Double, shouldUpdateLastLocation: Boolean
    ): Flow<Forecast?> = flow {
        try {
            val response = openMeteoNetworkDataSource.getForecast(latitude, longitude)
            if (response.isSuccessful) {
                val forecastResponse = response.body()
                if (forecastResponse != null) {
                    forecastResponse.let { forecastDto ->
                        val forecast = ForecastResponseMapper.Forecast(forecastDto).invoke()
                        // Save data into DB too while emitting it
                        updateCorrespondingLatLong(
                            latitude,
                            longitude,
                            forecast,
                            shouldUpdateLastLocation
                        )

                        emit(forecast.also {
                            saveForecast(it)
                        })
                    }
                } else {
                    emit(null)
                }
            } else {
                emit(null)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Update the local database
     */
    private suspend fun saveForecast(forecast: Forecast) {
        try {
            weatherDatabaseDataSource.saveForecast(
                ForecastDbMapper.fromBusiness(
                    forecast
                )
            )
            weatherDatabaseDataSource.saveCurrentUnits(
                CurrentUnitsDbMapper.fromBusiness(
                    forecast.currentUnits, forecast.latitude, forecast.longitude
                )
            )
            weatherDatabaseDataSource.saveCurrent(
                CurrentDbMapper.fromBusiness(
                    forecast.current, forecast.latitude, forecast.longitude
                )
            )
            weatherDatabaseDataSource.saveHourlyUnits(
                HourlyUnitsDbMapper.fromBusiness(
                    forecast.hourlyUnits, forecast.latitude, forecast.longitude
                )
            )
            HourlyDbMapper.fromBusiness(
                forecast.hourly, forecast.latitude, forecast.longitude
            ).forEach {
                weatherDatabaseDataSource.saveHourly(it)
            }

            weatherDatabaseDataSource.saveDailyUnits(
                DailyUnitsDbMapper.fromBusiness(
                    forecast.dailyUnits, forecast.latitude, forecast.longitude
                )
            )

            DailyDbMapper.fromBusiness(
                forecast.daily, forecast.latitude, forecast.longitude
            ).forEach {
                weatherDatabaseDataSource.saveDaily(it)
            }

            Log.d(TAG, "saveForecast: Forecast saved successfully!")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private suspend fun updateCorrespondingLatLong(
        latitude: Double, longitude: Double, forecast: Forecast, shouldUpdateLastLocation: Boolean
    ) {
        if (shouldUpdateLastLocation) {
            val deviceLocation = lastLocationInteractorApi.getDeviceLocation()
            val lastLocation = deviceLocation?.let {
                it.copy(
                    correspondingForecastLat = forecast.latitude,
                    correspondingForecastLong = forecast.longitude
                )
            }
            lastLocation?.let {
                lastLocationInteractorApi.saveDeviceLocation(it)
            }
        } else {
            val searchedPlaceByLatLong = locationSearchInteractorApi.getSearchedPlaceByLatLong(
                latitude, longitude
            )
            val place = searchedPlaceByLatLong?.let {
                it.copy(
                    correspondingForecastLat = forecast.latitude,
                    correspondingForecastLong = forecast.longitude
                )
            }
            place?.let {
                locationSearchInteractorApi.saveSearchedPlace(place)
            }
        }
    }

    companion object {
        private const val TAG = "OpenMeteoRemoteRepo"
    }
}