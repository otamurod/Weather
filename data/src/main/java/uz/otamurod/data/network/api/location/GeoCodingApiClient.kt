package uz.otamurod.data.network.api.location

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.otamurod.specs.buildconfig.WeatherBuildConfigApi

class GeoCodingApiClient(okHttpClient: OkHttpClient, weatherBuildConfigApi: WeatherBuildConfigApi) {
    companion object {
        private fun getRetrofitInstance(
            okHttpClient: OkHttpClient,
            weatherBuildConfigApi: WeatherBuildConfigApi
        ): Retrofit {
            val baseUrl = weatherBuildConfigApi.geoCodingApiBaseUrl

            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }

    val geoCodingApiService: GeoCodingApiService =
        getRetrofitInstance(
            okHttpClient,
            weatherBuildConfigApi
        ).create(GeoCodingApiService::class.java)
}