package uz.otamurod.weather.app.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.otamurod.data.database.datasource.WeatherDatabaseDataSource
import uz.otamurod.data.network.datasource.location.GeoCodingNetworkDataSource
import uz.otamurod.data.network.datasource.weather.OpenMeteoNetworkDataSource
import uz.otamurod.data.repository.GeoCodingRemoteRepository
import uz.otamurod.data.repository.OpenMeteoRemoteRepository
import uz.otamurod.data.repository.WeatherLocalRepository
import uz.otamurod.domain.api.repository.GeoCodingRemoteRepositoryApi
import uz.otamurod.domain.api.repository.OpenMeteoRemoteRepositoryApi
import uz.otamurod.domain.api.repository.WeatherLocalRepositoryApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    /**
     * Remote Repository Module
     */

    @Provides
    @Singleton
    fun provideOpenMeteoRemoteRepositoryApi(
        openMeteoNetworkDataSource: OpenMeteoNetworkDataSource
    ): OpenMeteoRemoteRepositoryApi {
        return OpenMeteoRemoteRepository(openMeteoNetworkDataSource)
    }

    @Provides
    @Singleton
    fun provideGeoCodingRemoteRepositoryApi(
        geoCodingNetworkDataSource: GeoCodingNetworkDataSource
    ): GeoCodingRemoteRepositoryApi {
        return GeoCodingRemoteRepository(geoCodingNetworkDataSource)
    }

    /**
     * Local Repository Module
     */

    @Provides
    @Singleton
    fun provideWeatherLocalRepositoryApi(
    weatherDatabaseDataSource: WeatherDatabaseDataSource
    ): WeatherLocalRepositoryApi {
        return WeatherLocalRepository(weatherDatabaseDataSource)
    }
}