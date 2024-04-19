package uz.otamurod.presentation.ui.weekly_forecast

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.otamurod.domain.interactor.ForecastInteractorApi
import javax.inject.Inject

@HiltViewModel
class WeeklyForecastViewModel @Inject constructor(
    private val forecastInteractorApi: ForecastInteractorApi
): ViewModel() {

}