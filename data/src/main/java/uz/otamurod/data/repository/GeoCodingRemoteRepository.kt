package uz.otamurod.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import uz.otamurod.data.network.datasource.location.GeoCodingNetworkDataSource
import uz.otamurod.data.network.mapper.location.LocationSearchResponseMapper
import uz.otamurod.domain.api.model.location.LocationSearch
import uz.otamurod.domain.api.repository.GeoCodingRemoteRepositoryApi
import uz.otamurod.domain.util.DataState

class GeoCodingRemoteRepository(
    private val geoCodingNetworkDataSource: GeoCodingNetworkDataSource
) : GeoCodingRemoteRepositoryApi {
    override suspend fun searchLocation(
        name: String,
        language: String
    ): Flow<DataState<LocationSearch>> = flow {
        try {
            emit(DataState.Loading)

            val response = geoCodingNetworkDataSource.searchLocation(name, language)
            if (response.isSuccessful) {
                val dataState = response.body()
                if (dataState != null) {
                    val locationSearchResponse = dataState.data
                    locationSearchResponse?.let {
                        val locationSearch =
                            LocationSearchResponseMapper.LocationSearch(it).invoke()
                        emit(DataState.Success(locationSearch))
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