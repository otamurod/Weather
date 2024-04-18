package uz.otamurod.data.repository

import uz.otamurod.data.database.datasource.WeatherDatabaseDataSource
import uz.otamurod.data.database.mapper.lastlocation.LastLocationDbMapper
import uz.otamurod.data.database.mapper.place.PlaceDbMapper
import uz.otamurod.data.database.mapper.weather.ForecastDbMapper
import uz.otamurod.domain.api.model.location.Place
import uz.otamurod.domain.api.model.weather.Forecast
import uz.otamurod.domain.api.repository.WeatherLocalRepositoryApi
import uz.otamurod.domain.model.LastLocation
import javax.inject.Inject

class WeatherLocalRepository @Inject constructor(
    private val weatherDatabaseDataSource: WeatherDatabaseDataSource
) : WeatherLocalRepositoryApi {

    override suspend fun getDeviceLocation(): LastLocation {
        return LastLocationDbMapper.fromDto(weatherDatabaseDataSource.getLastLocation())
    }

    override suspend fun getSearchedPlaceById(id: Int): Place {
        return PlaceDbMapper.fromDto(weatherDatabaseDataSource.getPlaceById(id))
    }

    override suspend fun getAllSearchedPlaces(): List<Place> {
        return weatherDatabaseDataSource.getAllPlaces().map {
            PlaceDbMapper.fromDto(it)
        }
    }

    override suspend fun getForecastOfPlaceByLatLong(
        latitude: Double,
        longitude: Double
    ): Forecast? {
        val forecastEntity = weatherDatabaseDataSource.getForecastByLatLong(latitude, longitude)

        return if (forecastEntity != null) {
            val currentUnitsEntity =
                weatherDatabaseDataSource.getCurrentUnitsByForecastId(forecastEntity.id)

            val currentEntity =
                weatherDatabaseDataSource.getCurrentByForecastId(forecastEntity.id)

            val hourlyUnitsEntity =
                weatherDatabaseDataSource.getHourlyUnitsByForecastId(forecastEntity.id)

            val hourlyEntities =
                weatherDatabaseDataSource.getHourlyByForecastId(forecastEntity.id)

            val dailyUnitsEntity =
                weatherDatabaseDataSource.getDailyUnitsByForecastId(forecastEntity.id)

            val dailyEntities =
                weatherDatabaseDataSource.getDailyByForecastId(forecastEntity.id)

            if (
                currentUnitsEntity != null &&
                currentEntity != null &&
                hourlyUnitsEntity != null &&
                !hourlyEntities.isNullOrEmpty() &&
                dailyUnitsEntity != null &&
                !dailyEntities.isNullOrEmpty()
            ) {
                ForecastDbMapper.fromDto(
                    forecastEntity,
                    currentUnitsEntity,
                    currentEntity,
                    hourlyUnitsEntity,
                    hourlyEntities,
                    dailyUnitsEntity,
                    dailyEntities
                )
            } else {
                null
            }
        } else {
            null
        }
    }

    override suspend fun insertDeviceLocation(lastLocation: LastLocation) {
        weatherDatabaseDataSource.insertLastLocation(LastLocationDbMapper.fromBusiness(lastLocation))
    }

    override suspend fun insertSearchedPlace(place: Place) {
        weatherDatabaseDataSource.insertPlace(PlaceDbMapper.fromBusiness(place))
    }

    override suspend fun insertForecastOfPlace(forecast: Forecast) {
        weatherDatabaseDataSource.insertForecast(ForecastDbMapper.fromBusiness(forecast))
    }

    override suspend fun updateDeviceLocation(lastLocation: LastLocation) {
        weatherDatabaseDataSource.updateLastLocation(LastLocationDbMapper.fromBusiness(lastLocation))
    }

    override suspend fun deleteSearchedPlaceById(id: Int) {
        weatherDatabaseDataSource.deletePlaceById(id)
    }
}