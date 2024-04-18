package uz.otamurod.data.di.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.otamurod.data.network.datasource.location.GeoCodingNetworkDataSource
import uz.otamurod.data.network.datasource.weather.OpenMeteoNetworkDataSource
import uz.otamurod.data.repository.GeoCodingRemoteRepository
import uz.otamurod.data.repository.OpenMeteoRemoteRepository
import uz.otamurod.domain.api.repository.GeoCodingRemoteRepositoryApi
import uz.otamurod.domain.api.repository.OpenMeteoRemoteRepositoryApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    /**
     * Remote Repository Module
     */

    @Provides
    @Singleton
    fun provideOpenMeteoRemoteRepository(
        openMeteoNetworkDataSource: OpenMeteoNetworkDataSource
    ): OpenMeteoRemoteRepositoryApi {
        return OpenMeteoRemoteRepository(openMeteoNetworkDataSource)
    }

    @Provides
    @Singleton
    fun provideGeoCodingRemoteRepository(
        geoCodingNetworkDataSource: GeoCodingNetworkDataSource
    ): GeoCodingRemoteRepositoryApi {
        return GeoCodingRemoteRepository(geoCodingNetworkDataSource)
    }
}