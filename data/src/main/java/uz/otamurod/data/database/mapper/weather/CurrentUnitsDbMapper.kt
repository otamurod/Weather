package uz.otamurod.data.database.mapper.weather

import uz.otamurod.data.database.entity.weather.CurrentUnitsEntity
import uz.otamurod.domain.api.model.weather.CurrentUnits

object CurrentUnitsDbMapper {
    fun fromBusiness(currentUnits: CurrentUnits, latitude: Double, longitude: Double) =
        with(currentUnits) {
            CurrentUnitsEntity(
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

    fun fromDto(currentUnitsEntity: CurrentUnitsEntity): CurrentUnits = with(currentUnitsEntity) {
        return CurrentUnits(
            time = this.time,
            interval = this.interval,
            temperature2m = this.temperature2m,
            apparentTemperature = this.apparentTemperature,
            isDay = this.isDay,
            weatherCode = this.weatherCode
        )
    }
}
