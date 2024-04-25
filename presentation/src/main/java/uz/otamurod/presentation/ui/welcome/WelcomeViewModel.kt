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

    suspend fun setContext(context: Context, networkStatusListener: NetworkStatusListener?) {
        currentLocationTracker = CurrentLocationTracker(context)
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
        val location = currentLocationTracker.getLocation()
        if (location != null) {
            val addressName =
                currentLocationTracker.getAddressName(location.latitude, location.longitude)

            if (lastLocation.value != null && isLastLocationAccessed.value != null && isLastLocationAccessed.value == true) {
                viewModelScope.launch(Dispatchers.IO) {
                    val updatedLastLocation = lastLocation.value?.copy(
                        latitude = location.latitude,
                        longitude = location.longitude,
                        addressName = addressName
                    )

                    if (updatedLastLocation != null) {
                        lastLocationInteractorApi.saveDeviceLocation(updatedLastLocation)
                        withContext(Dispatchers.Main) {
                            updatePromptFlags(
                                showGrantPermissionPrompt = false,
                                showGpsEnablePrompt = false,
                                showTurnOnNetworkPrompt = false
                            )
                            updateLastLocationAccessResult(updatedLastLocation, true)
                        }
                    } else {
                        updateLastLocationAccessResult(null, false)
                    }
                }
            } else {
                viewModelScope.launch(Dispatchers.IO) {
                    val lastLocation =
                        LastLocation(0, location.latitude, location.longitude, addressName)

                    lastLocationInteractorApi.saveDeviceLocation(lastLocation)
                    withContext(Dispatchers.Main) {
                        updateLastLocationAccessResult(lastLocation, true)
                    }
                }
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

    private fun retrieveAppVersionName() {
        viewModelScope.launch {
            appVersionName.setValue(weatherBuildConfigApi.buildVersion)
        }
    }

}