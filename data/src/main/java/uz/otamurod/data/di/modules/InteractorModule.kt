package uz.otamurod.data.di.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.otamurod.data.interactor.ForecastInteractor
import uz.otamurod.data.interactor.LocationSearchInteractor
import uz.otamurod.domain.interactor.ForecastInteractorApi
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
    fun provideForecastInteractor(forecastInteractor: ForecastInteractor): ForecastInteractorApi {
        return forecastInteractor
    }

    @Provides
    @Singleton
    fun provideLocationSearchInteractor(locationSearchInteractor: LocationSearchInteractor): LocationSearchInteractorApi {
        return locationSearchInteractor
    }
}