package uz.otamurod.data.database.mapper.lastlocation

import uz.otamurod.data.database.entity.lastlocation.LastLocationEntity
import uz.otamurod.domain.model.LastLocation

object LastLocationDbMapper {
    fun fromBusiness(lastLocation: LastLocation) = with(lastLocation) {
        LastLocationEntity(
            id = this.id,
            latitude = this.latitude,
            longitude = this.longitude,
            addressName = this.addressName,
            correspondingForecastLat = this.correspondingForecastLat,
            correspondingForecastLong = this.correspondingForecastLong
        )
    }

    fun fromDto(lastLocationEntity: LastLocationEntity): LastLocation = with(lastLocationEntity) {
        LastLocation(
            id = this.id,
            latitude = this.latitude,
            longitude = this.longitude,
            addressName = this.addressName,
            correspondingForecastLat = this.correspondingForecastLat,
            correspondingForecastLong = this.correspondingForecastLong
        )
    }
}
