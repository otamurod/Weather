package uz.otamurod.domain.interactor

import kotlinx.coroutines.flow.Flow
import uz.otamurod.domain.api.model.location.LocationSearch
import uz.otamurod.domain.util.DataState

interface LocationSearchInteractorApi {
    suspend fun searchLocation(
        name: String,
        language: String = "en"
    ): Flow<DataState<LocationSearch>>
}