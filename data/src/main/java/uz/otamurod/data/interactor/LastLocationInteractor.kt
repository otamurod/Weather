package uz.otamurod.data.interactor

import uz.otamurod.domain.api.repository.WeatherLocalRepositoryApi
import uz.otamurod.domain.interactor.LastLocationInteractorApi
import uz.otamurod.domain.model.LastLocation
import javax.inject.Inject

class LastLocationInteractor @Inject constructor(
    private val weatherLocalRepositoryApi: WeatherLocalRepositoryApi
) : LastLocationInteractorApi {

    override suspend fun getDeviceLocation(): LastLocation {
        return weatherLocalRepositoryApi.getDeviceLocation()
    }

    override suspend fun insertDeviceLocation(lastLocation: LastLocation) {
        return weatherLocalRepositoryApi.insertDeviceLocation(lastLocation)
    }

    override suspend fun updateDeviceLocation(lastLocation: LastLocation) {
        return weatherLocalRepositoryApi.updateDeviceLocation(lastLocation)
    }
}