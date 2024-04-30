package uz.otamurod.weather.app.domain.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uz.otamurod.data.database.dao.lastlocation.LastLocationDao
import uz.otamurod.data.database.dao.place.PlaceDao
import uz.otamurod.data.database.dao.weather.*
import uz.otamurod.data.database.datasource.WeatherDatabaseDataSource
import uz.otamurod.data.database.db.WeatherDatabase
import javax.inject.Singleton

/**
 *  Database Module
 */

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideWeatherDatabase(@ApplicationContext context: Context): WeatherDatabase {
        return WeatherDatabase.getInstance(context)
    }

    @Provides
    fun provideLastLocationDao(weatherDatabase: WeatherDatabase): LastLocationDao {
        return weatherDatabase.lastLocationDao()
    }

    @Provides
    fun providePlaceDao(weatherDatabase: WeatherDatabase): PlaceDao {
        return weatherDatabase.placeDao()
    }

    @Provides
    fun provideCurrentDao(weatherDatabase: WeatherDatabase): CurrentDao {
        return weatherDatabase.currentDao()
    }

    @Provides
    fun provideCurrentUnitsDao(weatherDatabase: WeatherDatabase): CurrentUnitsDao {
        return weatherDatabase.currentUnitsDao()
    }

    @Provides
    fun provideDailyDao(weatherDatabase: WeatherDatabase): DailyDao {
        return weatherDatabase.dailyDao()
    }

    @Provides
    fun provideDailyUnitsDao(weatherDatabase: WeatherDatabase): DailyUnitsDao {
        return weatherDatabase.dailyUnitsDao()
    }

    @Provides
    fun provideHourlyDao(weatherDatabase: WeatherDatabase): HourlyDao {
        return weatherDatabase.hourlyDao()
    }

    @Provides
    fun provideHourlyUnitsDao(weatherDatabase: WeatherDatabase): HourlyUnitsDao {
        return weatherDatabase.hourlyUnitsDao()
    }

    @Provides
    fun provideForecastDao(weatherDatabase: WeatherDatabase): ForecastDao {
        return weatherDatabase.forecastDao()
    }

    @Provides
    @Singleton
    fun provideWeatherDatabaseDataSource(
        lastLocationDao: LastLocationDao,
        placeDao: PlaceDao,
        currentDao: CurrentDao,
        currentUnitsDao: CurrentUnitsDao,
        dailyDao: DailyDao,
        dailyUnitsDao: DailyUnitsDao,
        hourlyDao: HourlyDao,
        hourlyUnitsDao: HourlyUnitsDao,
        forecastDao: ForecastDao
    ): WeatherDatabaseDataSource {
        return WeatherDatabaseDataSource(
            lastLocationDao,
            placeDao,
            currentDao,
            currentUnitsDao,
            dailyDao,
            dailyUnitsDao,
            hourlyDao,
            hourlyUnitsDao,
            forecastDao
        )
    }
}