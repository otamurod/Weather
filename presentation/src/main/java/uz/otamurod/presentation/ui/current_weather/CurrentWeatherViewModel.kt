package uz.otamurod.presentation.ui.current_weather

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
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
import java.io.IOException
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
    val isForecastFetched: LiveData<Boolean> by lazy { MutableLiveData() }
    val shouldUpdateLastLocation: LiveData<Boolean> by lazy { MutableLiveData() }

    val dataState: LiveData<DataState<Forecast>> by lazy { MutableLiveData() }
    val lastLocation: LiveData<LastLocation> by lazy { MutableLiveData() }
    val forecast: LiveData<Forecast> by lazy { MutableLiveData() }
    val searchedPlace: LiveData<Place> by lazy { MutableLiveData() }
    val shouldShowTurnOnNetworkPrompt: LiveData<Boolean> by lazy { MutableLiveData() }

    init {
        viewModelScope.launch {
            updateForecastFetchedFlag(false)
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

    suspend fun getForecast(
        latitude: Double,
        longitude: Double,
        shouldUpdateLastLocation: Boolean
    ) {
        withContext(Dispatchers.Main) {
            this@CurrentWeatherViewModel.shouldUpdateLastLocation.setValue(shouldUpdateLastLocation)
        }
        viewModelScope.launch(Dispatchers.IO) {
            forecastInteractorApi.getForecast(latitude, longitude, shouldUpdateLastLocation)
                .onStart {
                    withContext(Dispatchers.Main) {
                        dataState.setValue(DataState.Loading)
                    }
                }.catch {
                    withContext(Dispatchers.Main) {
                        dataState.setValue(DataState.Error(it.message.toString()))
                        if (it is IOException) {
                            shouldShowTurnOnNetworkPrompt.setValue(true)
                        }
                    }
                }.collectLatest {
                    when (it) {
                        is DataState.Loading -> {
                            withContext(Dispatchers.Main) {
                                dataState.setValue(DataState.Loading)
                                updateForecastFetchedFlag(false)
                            }
                        }
                        is DataState.Success -> {
                            withContext(Dispatchers.Main) {
                                shouldShowTurnOnNetworkPrompt.setValue(false)
                                dataState.setValue(it)
                                updateForecastFetchedFlag(true)
                                forecast.setValue(it.data)
                            }
                        }
                        is DataState.Error -> {
                            withContext(Dispatchers.Main) {
                                updateForecastFetchedFlag(false)
                                dataState.setValue(DataState.Error(it.message.toString()))
                                shouldShowTurnOnNetworkPrompt.setValue(true)
                            }
                        }
                    }
                }
        }
    }

    fun updateForecastFetchedFlag(isFetched: Boolean) {
        isForecastFetched.setValue(isFetched)
    }

    suspend fun updateLocationCorrespondingFields() {
        forecast.value?.let {
            /**
             * We should  update them so that we can create a relation between Forecast and Location!
             * We have already updated the corresponding Lat & Long fields in OpenMeteoRepository
             * To update our LiveData, we just need to retrieve them from Database
             */
            getDeviceLocation()
            searchedPlace.value?.let { getSearchedPlaceById(it.id) }
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