package uz.otamurod.presentation.ui.location_search

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import uz.otamurod.domain.api.model.location.LocationSearch
import uz.otamurod.domain.api.model.location.Place
import uz.otamurod.domain.interactor.LocationSearchInteractorApi
import uz.otamurod.domain.util.DataState
import uz.otamurod.presentation.ui.base.BaseViewModel
import uz.otamurod.presentation.utils.network.NetworkStatusListener
import uz.otamurod.presentation.utils.network.isNetworkConnected
import javax.inject.Inject

@HiltViewModel
class LocationSearchViewModel @Inject constructor(
    private val locationSearchInteractorApi: LocationSearchInteractorApi
) : BaseViewModel() {
    private val context: LiveData<Context> by lazy { MutableLiveData() }

    val isNetworkConnected: LiveData<Boolean> by lazy { MutableLiveData() }
    val dataState: LiveData<DataState<LocationSearch>> by lazy { MutableLiveData() }
    val locationSearchResult: LiveData<LocationSearch> by lazy { MutableLiveData() }
    val searchedPlaces: LiveData<List<Place>> by lazy { MutableLiveData() }

    init {
        viewModelScope.launch {
            getAllSearchedPlaces()
        }
    }

    suspend fun searchLocation(
        name: String,
        language: String = "en"
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            if (isNetworkConnected.value == true) {
                locationSearchInteractorApi.searchLocation(name, language)
                    .collectLatest {
                        when (it) {
                            is DataState.Loading -> {
                                withContext(Dispatchers.Main) {
                                    dataState.setValue(DataState.Loading)
                                }
                            }
                            is DataState.Success -> {
                                withContext(Dispatchers.Main) {
                                    dataState.setValue(it)
                                    locationSearchResult.setValue(it.data)
                                }
                            }
                            is DataState.Error -> {
                                withContext(Dispatchers.Main) {
                                    dataState.setValue(DataState.Error(it.message.toString()))
                                }
                            }
                        }
                    }
            } else {
                isNetworkConnected.setValue(false)
            }
        }
    }

    private suspend fun getAllSearchedPlaces() {
        viewModelScope.launch(Dispatchers.IO) {
            val allSearchedPlaces = locationSearchInteractorApi.getAllSearchedPlaces()
            Log.d(TAG, "getAllSearchedPlaces: $allSearchedPlaces")
            if (allSearchedPlaces.isNotEmpty()) {
                withContext(Dispatchers.Main) {
                    searchedPlaces.setValue(allSearchedPlaces)
                }
            }
        }
    }

    suspend fun insertSearchedPlace(place: Place) {
        viewModelScope.launch(Dispatchers.IO) {
            if ((searchedPlaces.value != null && !searchedPlaces.value!!.contains(place) || searchedPlaces.value == null)) {
                locationSearchInteractorApi.insertSearchedPlace(place)
            }
            getAllSearchedPlaces()
        }
    }

    suspend fun deleteSearchedPlaceById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            locationSearchInteractorApi.deleteSearchedPlaceById(id)
            getAllSearchedPlaces()
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

    companion object {
        private const val TAG = "LocationSearchViewModel"
    }

}