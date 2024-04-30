package uz.otamurod.data.repository

import uz.otamurod.data.database.datasource.WeatherDatabaseDataSource
import uz.otamurod.data.database.mapper.lastlocation.LastLocationDbMapper
import uz.otamurod.data.database.mapper.place.PlaceDbMapper
import uz.otamurod.domain.api.model.location.Place
import uz.otamurod.domain.api.repository.WeatherLocalRepositoryApi
import uz.otamurod.domain.model.LastLocation
import javax.inject.Inject

class WeatherLocalRepository @Inject constructor(
    private val weatherDatabaseDataSource: WeatherDatabaseDataSource
) : WeatherLocalRepositoryApi {

    override suspend fun getDeviceLocation(): LastLocation? {
        val lastLocationEntity = weatherDatabaseDataSource.getLastLocation()
        return if (lastLocationEntity != null) {
            LastLocationDbMapper.fromDto(lastLocationEntity)
        } else {
            null
        }
    }

    override suspend fun getSearchedPlaceById(id: Int): Place? {
        return PlaceDbMapper.fromDto(weatherDatabaseDataSource.getPlaceById(id))
    }

    override suspend fun getSearchedPlaceByLatLong(latitude: Double, longitude: Double): Place? {
        return PlaceDbMapper.fromDto(weatherDatabaseDataSource.getPlaceByLatLong(latitude, longitude))
    }

    override suspend fun getAllSearchedPlaces(): List<Place> {
        return weatherDatabaseDataSource.getAllPlaces().map {
            PlaceDbMapper.fromDto(it)!!
        }
    }

    override suspend fun saveDeviceLocation(lastLocation: LastLocation) {
        weatherDatabaseDataSource.saveLastLocation(LastLocationDbMapper.fromBusiness(lastLocation))
    }

    override suspend fun saveSearchedPlace(place: Place) {
        weatherDatabaseDataSource.savePlace(PlaceDbMapper.fromBusiness(place))
    }

    override suspend fun deleteSearchedPlaceById(id: Int) {
        weatherDatabaseDataSource.deletePlaceById(id)
    }

    override suspend fun deleteForecast(latitude: Double, longitude: Double) {
        weatherDatabaseDataSource.deleteForecast(latitude, longitude)
    }
}