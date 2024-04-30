package uz.otamurod.domain.api.repository

import uz.otamurod.domain.api.model.location.Place
import uz.otamurod.domain.model.LastLocation

interface WeatherLocalRepositoryApi {
    /**
     * Retrieve from Database
     */

    // Device Location
    suspend fun getDeviceLocation(): LastLocation?

    // Searched Location
    suspend fun getSearchedPlaceById(id: Int): Place?

    suspend fun getSearchedPlaceByLatLong(latitude: Double, longitude: Double): Place?

    suspend fun getAllSearchedPlaces(): List<Place>

    /**
     * Insert to Database
     */

    // Device Location
    suspend fun saveDeviceLocation(lastLocation: LastLocation)

    // Searched Location
    suspend fun saveSearchedPlace(place: Place)

    /**
     * Clean Database
     */

    // Searched Location
    suspend fun deleteSearchedPlaceById(id: Int)

    suspend fun deleteForecast(latitude: Double, longitude: Double)
}