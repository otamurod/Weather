package uz.otamurod.data.network.datasource.location

import retrofit2.Response
import uz.otamurod.data.network.api.location.GeoCodingApiService
import uz.otamurod.data.network.entities.location.LocationSearchResponse
import javax.inject.Inject

class GeoCodingNetworkDataSource @Inject constructor(
    private val geoCodingApiService: GeoCodingApiService
) {
    suspend fun searchLocation(
        name: String,
        language: String
    ): Response<LocationSearchResponse> {
        return geoCodingApiService.searchLocation(name = name, language = language)
    }
}