package uz.otamurod.data.interactor

import kotlinx.coroutines.flow.Flow
import uz.otamurod.domain.api.model.location.LocationSearch
import uz.otamurod.domain.api.repository.GeoCodingRemoteRepositoryApi
import uz.otamurod.domain.interactor.LocationSearchInteractorApi
import uz.otamurod.domain.util.DataState

class LocationSearchInteractor(
    private val geoCodingRemoteRepositoryApi: GeoCodingRemoteRepositoryApi
) : LocationSearchInteractorApi {
    override suspend fun searchLocation(
        name: String,
        language: String
    ): Flow<DataState<LocationSearch>> {
        return geoCodingRemoteRepositoryApi.searchLocation(name, language)
    }
}