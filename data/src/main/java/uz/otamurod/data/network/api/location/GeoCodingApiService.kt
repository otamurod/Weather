package uz.otamurod.data.network.api.location

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import uz.otamurod.data.network.entities.location.LocationSearchResponse
import uz.otamurod.domain.util.DataState

interface GeoCodingApiService {

    // https://geocoding-api.open-meteo.com/v1/search?name=jizzax&count=10&language=en&format=json

    @GET("search")
    suspend fun searchLocation(
        @Query("name") name: String,
        @Query("count") count: Int = 10,
        @Query("language") language: String,
        @Query("format") format: String = "json"
    ): Response<DataState<LocationSearchResponse>>

}