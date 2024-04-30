package uz.otamurod.data.interactor

import kotlinx.coroutines.flow.Flow
import uz.otamurod.domain.api.model.location.LocationSearch
import uz.otamurod.domain.api.model.location.Place
import uz.otamurod.domain.api.repository.GeoCodingRemoteRepositoryApi
import uz.otamurod.domain.api.repository.WeatherLocalRepositoryApi
import uz.otamurod.domain.interactor.LocationSearchInteractorApi
import uz.otamurod.domain.util.DataState
import javax.inject.Inject

class LocationSearchInteractor @Inject constructor(
    private val geoCodingRemoteRepositoryApi: GeoCodingRemoteRepositoryApi,
    private val weatherLocalRepositoryApi: WeatherLocalRepositoryApi
) : LocationSearchInteractorApi {
    override suspend fun searchLocation(
        name: String,
        language: String
    ): Flow<DataState<LocationSearch>> {
        return geoCodingRemoteRepositoryApi.searchLocation(name, language)
    }

    override suspend fun getSearchedPlaceById(id: Int): Place? {
        return weatherLocalRepositoryApi.getSearchedPlaceById(id)
    }

    override suspend fun getSearchedPlaceByLatLong(latitude: Double, longitude: Double): Place? {
        return weatherLocalRepositoryApi.getSearchedPlaceByLatLong(latitude, longitude)
    }

    override suspend fun getAllSearchedPlaces(): List<Place> {
        return weatherLocalRepositoryApi.getAllSearchedPlaces()
    }

    override suspend fun saveSearchedPlace(place: Place) {
        return weatherLocalRepositoryApi.saveSearchedPlace(place)
    }

    override suspend fun deleteSearchedPlaceById(id: Int) {
        return weatherLocalRepositoryApi.deleteSearchedPlaceById(id)
    }
}