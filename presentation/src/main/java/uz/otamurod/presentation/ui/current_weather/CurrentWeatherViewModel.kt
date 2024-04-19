package uz.otamurod.presentation.ui.current_weather

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.otamurod.domain.interactor.ForecastInteractorApi
import uz.otamurod.domain.interactor.LastLocationInteractorApi
import uz.otamurod.domain.interactor.LocationSearchInteractorApi
import javax.inject.Inject

@HiltViewModel
class CurrentWeatherViewModel @Inject constructor(
    private val lastLocationInteractorApi: LastLocationInteractorApi,
    private val forecastInteractorApi: ForecastInteractorApi,
    private val locationSearchInteractorApi: LocationSearchInteractorApi
) : ViewModel() {

}