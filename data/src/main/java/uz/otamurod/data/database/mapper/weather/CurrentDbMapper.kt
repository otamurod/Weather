package uz.otamurod.data.database.mapper.weather

import uz.otamurod.data.database.entity.weather.CurrentEntity
import uz.otamurod.domain.api.model.weather.Current

object CurrentDbMapper {
    fun fromBusiness(current: Current, latitude: Double, longitude: Double) = with(current) {
        CurrentEntity(
            latitude = latitude,
            longitude = longitude,
            time = this.time,
            interval = this.interval,
            temperature2m = this.temperature2m,
            apparentTemperature = this.apparentTemperature,
            isDay = this.isDay,
            weatherCode = this.weatherCode
        )
    }

    fun fromDto(currentEntity: CurrentEntity): Current = with(currentEntity) {
        Current(
            time = this.time,
            interval = this.interval,
            temperature2m = this.temperature2m,
            apparentTemperature = this.apparentTemperature,
            isDay = this.isDay,
            weatherCode = this.weatherCode
        )
    }
}
