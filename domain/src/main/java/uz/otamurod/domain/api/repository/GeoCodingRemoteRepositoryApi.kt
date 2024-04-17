package uz.otamurod.domain.api.repository

import kotlinx.coroutines.flow.Flow
import uz.otamurod.domain.api.model.location.LocationSearch
import uz.otamurod.domain.util.DataState

interface GeoCodingRemoteRepositoryApi {
    suspend fun searchLocation(
        name: String,
        language: String = "en"
    ): Flow<DataState<LocationSearch>>
}