package uz.otamurod.data.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uz.otamurod.data.preferences.WeatherApplicationPreferences
import uz.otamurod.domain.preferences.WeatherApplicationPreferencesApi
import uz.otamurod.specs.buildconfig.WeatherBuildConfigApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharedPreferencesModule {
    /**
     * Shared Preferences Module
     */

    @Singleton
    @Provides
    fun provideWeatherApplicationPreferences(
        @ApplicationContext context: Context,
        weatherBuildConfigApi: WeatherBuildConfigApi
    ): WeatherApplicationPreferencesApi {
        return WeatherApplicationPreferences(context, weatherBuildConfigApi.sharedPreferencesName)
    }
}