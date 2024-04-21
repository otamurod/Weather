package uz.otamurod.presentation.ui.current_weather

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import uz.otamurod.domain.api.model.location.Place
import uz.otamurod.domain.api.model.weather.Forecast
import uz.otamurod.domain.interactor.ForecastInteractorApi
import uz.otamurod.domain.interactor.LastLocationInteractorApi
import uz.otamurod.domain.interactor.LocationSearchInteractorApi
import uz.otamurod.domain.model.LastLocation
import uz.otamurod.domain.util.DataState
import uz.otamurod.presentation.ui.base.BaseViewModel
import uz.otamurod.presentation.utils.network.NetworkStatusListener
import uz.otamurod.presentation.utils.network.isNetworkConnected
import javax.inject.Inject

@HiltViewModel
class CurrentWeatherViewModel @Inject constructor(
    private val lastLocationInteractorApi: LastLocationInteractorApi,
    private val forecastInteractorApi: ForecastInteractorApi,
    private val locationSearchInteractorApi: LocationSearchInteractorApi,
) : BaseViewModel() {
    private val context: LiveData<Context> by lazy { MutableLiveData() }
    val isNetworkConnected: LiveData<Boolean> by lazy { MutableLiveData() }
    val isSettingsBottomSheetShown: LiveData<Boolean> by lazy { MutableLiveData() }

    val dataState: LiveData<DataState<Forecast>> by lazy { MutableLiveData() }
    val lastLocation: LiveData<LastLocation> by lazy { MutableLiveData() }
    val forecast: LiveData<Forecast> by lazy { MutableLiveData() }
    val searchedPlace: LiveData<Place> by lazy { MutableLiveData() }
    val shouldShowTurnOnNetworkPrompt: LiveData<Boolean> by lazy { MutableLiveData() }

    init {
        viewModelScope.launch {
            getDeviceLocation()
        }
    }

    suspend fun getDeviceLocation() {
        viewModelScope.launch(Dispatchers.IO) {
            val deviceLocation = lastLocationInteractorApi.getDeviceLocation()
            withContext(Dispatchers.Main) {
                lastLocation.setValue(deviceLocation)
            }
        }
    }

    suspend fun getForecast(latitude: Double, longitude: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            forecastInteractorApi.getForecast(latitude, longitude)
                .collectLatest {
                    when (it) {
                        is DataState.Loading -> {
                            withContext(Dispatchers.Main) {
                                dataState.setValue(DataState.Loading)
                            }
                        }
                        is DataState.Success -> {
                            withContext(Dispatchers.Main) {
                                shouldShowTurnOnNetworkPrompt.setValue(false)
                                dataState.setValue(it)
                                forecast.setValue(it.data)
                            }
                        }
                        is DataState.Error -> {
                            withContext(Dispatchers.Main) {
                                dataState.setValue(DataState.Error(it.message.toString()))
                                shouldShowTurnOnNetworkPrompt.setValue(true)
                            }
                        }
                    }
                }
        }
    }

    suspend fun getSearchedPlaceById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val searchedPlaceById = locationSearchInteractorApi.getSearchedPlaceById(id)
            withContext(Dispatchers.Main) {
                searchedPlace.setValue(searchedPlaceById)
            }
        }
    }

    suspend fun setContext(context: Context, networkStatusListener: NetworkStatusListener?) {
        this.context.postValue(context)

        if (isNetworkConnected(context, networkStatusListener)) {
            withContext(Dispatchers.Main) {
                isNetworkConnected.setValue(true)
            }
        } else {
            withContext(Dispatchers.Main) {
                isNetworkConnected.setValue(false)
            }
        }
    }

    fun updateNetworkStatus(isConnected: Boolean) {
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                isNetworkConnected.setValue(isConnected)
            }
        }
    }

    fun updateBottomSheetShouldShowFlag(isDialogShown: Boolean) {
        isSettingsBottomSheetShown.setValue(isDialogShown)
    }

}