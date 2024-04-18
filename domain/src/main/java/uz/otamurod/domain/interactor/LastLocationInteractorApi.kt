package uz.otamurod.domain.interactor

import uz.otamurod.domain.model.LastLocation

interface LastLocationInteractorApi {

    suspend fun getDeviceLocation(): LastLocation

    suspend fun insertDeviceLocation(lastLocation: LastLocation)

    suspend fun updateDeviceLocation(lastLocation: LastLocation)
}