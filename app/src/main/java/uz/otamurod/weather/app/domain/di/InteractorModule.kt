package uz.otamurod.weather.app.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.otamurod.data.interactor.ForecastInteractor
import uz.otamurod.data.interactor.LastLocationInteractor
import uz.otamurod.data.interactor.LocationSearchInteractor
import uz.otamurod.domain.interactor.ForecastInteractorApi
import uz.otamurod.domain.interactor.LastLocationInteractorApi
import uz.otamurod.domain.interactor.LocationSearchInteractorApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object InteractorModule {
    /**
     * Interactor Module
     */

    @Provides
    @Singleton
    fun provideForecastInteractorApi(forecastInteractor: ForecastInteractor): ForecastInteractorApi {
        return forecastInteractor
    }

    @Provides
    @Singleton
    fun provideLocationSearchInteractorApi(locationSearchInteractor: LocationSearchInteractor): LocationSearchInteractorApi {
        return locationSearchInteractor
    }

    @Provides
    @Singleton
    fun provideLastLocationInteractorApi(lastLocationInteractor: LastLocationInteractor): LastLocationInteractorApi {
        return lastLocationInteractor
    }
}