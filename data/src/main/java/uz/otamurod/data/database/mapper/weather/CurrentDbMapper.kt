package uz.otamurod.data.database.mapper.weather

import uz.otamurod.data.database.entity.weather.CurrentEntity
import uz.otamurod.domain.api.model.weather.Current

object CurrentDbMapper {
    fun fromBusiness(current: Current, forecastId: Int) = with(current) {
        CurrentEntity(
            forecastId = forecastId,
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
