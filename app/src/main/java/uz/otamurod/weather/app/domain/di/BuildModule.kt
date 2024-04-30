package uz.otamurod.weather.app.domain.di

import com.markodevcic.peko.PermissionRequester
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.otamurod.specs.buildconfig.WeatherBuildConfigApi
import uz.otamurod.weather.app.data.provider.buildconfig.WeatherBuildConfig
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BuildModule {
    /**
     * Build Module
     */
    @Provides
    @Singleton
    fun provideWeatherBuildConfigApi(): WeatherBuildConfigApi {
        return WeatherBuildConfig()
    }

    @Provides
    @Singleton
    fun providePermissionRequester(): PermissionRequester {
        return PermissionRequester.instance()
    }
}