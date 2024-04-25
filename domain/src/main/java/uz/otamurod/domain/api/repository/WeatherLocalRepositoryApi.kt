package uz.otamurod.domain.api.repository

import uz.otamurod.domain.api.model.location.Place
import uz.otamurod.domain.api.model.weather.Forecast
import uz.otamurod.domain.model.LastLocation

interface WeatherLocalRepositoryApi {
    /**
     * Retrieve from Database
     */

    // Device Location
    suspend fun getDeviceLocation(): LastLocation?

    // Searched Location
    suspend fun getSearchedPlaceById(id: Int): Place

    suspend fun getAllSearchedPlaces(): List<Place>

    // Weather
    suspend fun getForecastOfPlaceByLatLong(latitude: Double, longitude: Double): Forecast?

    /**
     * Insert to Database
     */

    // Device Location
    suspend fun saveDeviceLocation(lastLocation: LastLocation)

    // Searched Location
    suspend fun insertSearchedPlace(place: Place)

    // Weather
    suspend fun insertForecastOfPlace(forecast: Forecast)

    /**
     * Clean Database
     */

    // Searched Location
    suspend fun deleteSearchedPlaceById(id: Int)
}