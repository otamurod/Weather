package uz.otamurod.data.network.api.weather

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.otamurod.specs.buildconfig.WeatherBuildConfigApi

class OpenMeteoApiClient(okHttpClient: OkHttpClient, weatherBuildConfigApi: WeatherBuildConfigApi) {
    companion object {
        private fun getRetrofitInstance(
            okHttpClient: OkHttpClient,
            weatherBuildConfigApi: WeatherBuildConfigApi
        ): Retrofit {
            val baseUrl = weatherBuildConfigApi.openMateoApiBaseUrl

            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }

    val openMeteoApiService: OpenMeteoApiService =
        getRetrofitInstance(
            okHttpClient,
            weatherBuildConfigApi
        ).create(OpenMeteoApiService::class.java)
}