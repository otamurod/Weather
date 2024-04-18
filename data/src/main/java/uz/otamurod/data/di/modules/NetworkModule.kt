package uz.otamurod.data.di.modules

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import uz.otamurod.data.network.api.location.GeoCodingApiClient
import uz.otamurod.data.network.api.location.GeoCodingApiService
import uz.otamurod.data.network.api.weather.OpenMeteoApiClient
import uz.otamurod.data.network.api.weather.OpenMeteoApiService
import uz.otamurod.data.network.datasource.location.GeoCodingNetworkDataSource
import uz.otamurod.data.network.datasource.weather.OpenMeteoNetworkDataSource
import uz.otamurod.specs.buildconfig.WeatherBuildConfigApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    /**
     * Network Module
     */

    @Singleton
    @Provides
    fun provideOkHttpClient(
        @ApplicationContext context: Context
    ): OkHttpClient {
        val chuckInterceptor = ChuckerInterceptor.Builder(context).maxContentLength(500_000L)
            .redactHeaders("Content-Type", "application/json").alwaysReadResponseBody(true).build()

        return OkHttpClient.Builder().addInterceptor(chuckInterceptor).build()
    }

    @Provides
    @Singleton
    fun provideOpenMeteoApiService(
        okHttpClient: OkHttpClient,
        weatherBuildConfigApi: WeatherBuildConfigApi
    ): OpenMeteoApiService {
        return OpenMeteoApiClient(okHttpClient, weatherBuildConfigApi).openMeteoApiService
    }

    @Provides
    @Singleton
    fun provideGeoCodingApiService(
        okHttpClient: OkHttpClient,
        weatherBuildConfigApi: WeatherBuildConfigApi
    ): GeoCodingApiService {
        return GeoCodingApiClient(okHttpClient, weatherBuildConfigApi).geoCodingApiService
    }

    @Provides
    @Singleton
    fun provideOpenMeteoNetworkDataSource(openMeteoApiService: OpenMeteoApiService): OpenMeteoNetworkDataSource {
        return OpenMeteoNetworkDataSource(openMeteoApiService)
    }

    @Provides
    @Singleton
    fun provideGeoCodingNetworkDataSource(geoCodingApiService: GeoCodingApiService): GeoCodingNetworkDataSource {
        return GeoCodingNetworkDataSource(geoCodingApiService)
    }
}