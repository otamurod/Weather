package uz.otamurod.presentation.ui.welcome

import android.Manifest
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.markodevcic.peko.PermissionRequester
import com.markodevcic.peko.PermissionResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import uz.otamurod.domain.interactor.LastLocationInteractorApi
import uz.otamurod.domain.model.LastLocation
import uz.otamurod.domain.preferences.WeatherApplicationPreferencesApi
import uz.otamurod.presentation.ui.base.BaseViewModel
import uz.otamurod.presentation.utils.helper.CurrentLocationTracker
import uz.otamurod.presentation.utils.network.NetworkStatusListener
import uz.otamurod.presentation.utils.network.isNetworkConnected
import uz.otamurod.specs.buildconfig.WeatherBuildConfigApi
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val permissionRequester: PermissionRequester,
    private val lastLocationInteractorApi: LastLocationInteractorApi,
    private val weatherBuildConfigApi: WeatherBuildConfigApi
) : BaseViewModel() {
    private val context: LiveData<Context> by lazy { MutableLiveData() }
    private lateinit var currentLocationTracker: CurrentLocationTracker
    private val isNetworkConnected: LiveData<Boolean> by lazy { MutableLiveData() }
    private val isLastLocationAccessed: LiveData<Boolean> by lazy { MutableLiveData() }

    val permissionStateLiveData: LiveData<Boolean> by lazy { MutableLiveData() }
    val lastLocation: LiveData<LastLocation?> by lazy { MutableLiveData() }
    val shouldShowGpsEnablePrompt: LiveData<Boolean> by lazy { MutableLiveData() }
    val shouldShowTurnOnNetworkPrompt: LiveData<Boolean> by lazy { MutableLiveData() }
    val shouldShowGrantPermissionPrompt: LiveData<Boolean> by lazy { MutableLiveData() }
    val appVersionName: LiveData<String> by lazy { MutableLiveData() }

    init {
        requestLocationPermission()
        retrieveAppVersionName()
    }

    fun requestLocationPermission() = viewModelScope.launch {
        permissionRequester.request(
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
        ).collect {
            permissionStateLiveData.setValue(
                it is PermissionResult.Granted
            )

            refreshLastLocation()
        }
    }

    fun updateNetworkStatus(isConnected: Boolean) {
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                isNetworkConnected.setValue(isConnected)
            }
        }
    }

    suspend fun refreshLastLocation() {
        if (permissionStateLiveData.value == true) {
            checkGpsEnabled()
        } else {
            if (isLastLocationAccessed.value != null && isLastLocationAccessed.value == true) {
                accessLastLocation()
            } else {
                withContext(Dispatchers.Main) {
                    updateLastLocationAccessResult(null, false)

                    updatePromptFlags(
                        showGrantPermissionPrompt = true,
                        showGpsEnablePrompt = false,
                        showTurnOnNetworkPrompt = false
                    )
                }
            }
        }
    }

    suspend fun setContext(
        context: Context,
        networkStatusListener: NetworkStatusListener?,
        preferences: WeatherApplicationPreferencesApi
    ) {
        currentLocationTracker = CurrentLocationTracker(context, preferences)
        this.context.postValue(context)

        if (isNetworkConnected(context, networkStatusListener)) {
            withContext(Dispatchers.Main) {
                isNetworkConnected.setValue(true)
            }
        } else {
            accessLastLocation()
            withContext(Dispatchers.Main) {
                isNetworkConnected.setValue(false)
            }
        }
    }

    private fun updatePromptFlags(
        showGrantPermissionPrompt: Boolean,
        showGpsEnablePrompt: Boolean,
        showTurnOnNetworkPrompt: Boolean
    ) {
        shouldShowGrantPermissionPrompt.setValue(showGrantPermissionPrompt)
        shouldShowGpsEnablePrompt.setValue(showGpsEnablePrompt)
        shouldShowTurnOnNetworkPrompt.setValue(showTurnOnNetworkPrompt)
    }

    private fun updateLastLocationAccessResult(location: LastLocation?, isAccessed: Boolean) {
        lastLocation.setValue(location)
        isLastLocationAccessed.setValue(isAccessed)
    }

    private suspend fun checkGpsEnabled() {
        if (isNetworkConnected.value != null && isNetworkConnected.value == true) {
            val isGpsEnabled = currentLocationTracker.checkGpsEnabled()
            if (isGpsEnabled) {
                accessAndUpdateLastLocation()
            } else {
                accessLastLocation()
            }
        } else {
            withContext(Dispatchers.Main) {
                updateLastLocationAccessResult(null, false)

                updatePromptFlags(
                    showGrantPermissionPrompt = false,
                    showGpsEnablePrompt = false,
                    showTurnOnNetworkPrompt = true
                )
            }
        }
    }

    private fun accessLastLocation() {
        viewModelScope.launch(Dispatchers.IO) {
            val lastAccessedLocation = lastLocationInteractorApi.getDeviceLocation()
            if (lastAccessedLocation != null) {
                withContext(Dispatchers.Main) {
                    updatePromptFlags(
                        showGrantPermissionPrompt = false,
                        showGpsEnablePrompt = false,
                        showTurnOnNetworkPrompt = false
                    )
                    updateLastLocationAccessResult(lastAccessedLocation, true)
                }
            } else {
                withContext(Dispatchers.Main) {
                    updateLastLocationAccessResult(null, false)

                    updatePromptFlags(
                        showGrantPermissionPrompt = false,
                        showGpsEnablePrompt = true,
                        showTurnOnNetworkPrompt = false
                    )
                }
            }
        }
    }

    private suspend fun accessAndUpdateLastLocation() {
        // This is the device's real-time location
        val location = currentLocationTracker.getLocation()

        // This is the device location that is stored into Database
        // When the last real-time location is accessed
        val lastAccessedLocation = lastLocationInteractorApi.getDeviceLocation()

        // Check if the last known location is retrieved from Location Provider
        if (location != null) {
            // Case 1: SUCCESS
            viewModelScope.launch(Dispatchers.IO) {
                val addressName =
                    currentLocationTracker.getAddressName(location.latitude, location.longitude)

                // Create a Business Object
                val lastLocation = LastLocation(
                    id = 0, // We can use a unique id for LastLocation since a device can have only last known location
                    latitude = location.latitude,
                    longitude = location.longitude,
                    addressName = addressName,
                    // Initially use the same [latitude] & [longitude] for corresponding forecast info
                    // We are using this due to the Lat & Long of a Location and the Lat & Long of its corresponding Weather Forecast differs
                    correspondingForecastLat = location.latitude,
                    correspondingForecastLong = location.longitude
                )

                // Check if a user have weather forecast of the last known location
                // In case of the user has forecast data locally, we can use corresponding lat & long fields
                // So that we can retrieve the weather data from Database by simply passing the lat & long in [CurrentWeatherFragment]
                if (lastAccessedLocation != null &&
                    // We can also calculate the distance between the two locations
                    // And we can set an interval of difference to avoid unnecessary API calls
                    // Because the weather forecast of a place will be the same as its points covered in its territory
                    // For example, 0 to 2km distance
                    lastAccessedLocation.latitude == lastLocation.latitude &&
                    lastAccessedLocation.longitude == lastLocation.longitude
                ) {
                    lastLocation.correspondingForecastLat =
                        lastAccessedLocation.correspondingForecastLat
                    lastLocation.correspondingForecastLong =
                        lastAccessedLocation.correspondingForecastLong
                }
                lastLocationInteractorApi.saveDeviceLocation(lastLocation)
                withContext(Dispatchers.Main) {
                    updatePromptFlags(
                        showGrantPermissionPrompt = false,
                        showGpsEnablePrompt = false,
                        showTurnOnNetworkPrompt = false
                    )
                    updateLastLocationAccessResult(lastLocation, true)
                }
            }
        } else {
            // Case 2: FAILED
            withContext(Dispatchers.Main) {
                updateLastLocationAccessResult(null, false)

                updatePromptFlags(
                    showGrantPermissionPrompt = false,
                    showGpsEnablePrompt = true,
                    showTurnOnNetworkPrompt = false
                )
            }
        }
    }

    private fun retrieveAppVersionName() {
        viewModelScope.launch {
            appVersionName.setValue(weatherBuildConfigApi.buildVersion)
        }
    }

}