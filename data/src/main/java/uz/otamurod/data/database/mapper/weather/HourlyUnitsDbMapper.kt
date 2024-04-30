package uz.otamurod.data.database.mapper.weather

import uz.otamurod.data.database.entity.weather.HourlyUnitsEntity
import uz.otamurod.domain.api.model.weather.HourlyUnits

object HourlyUnitsDbMapper {
    fun fromBusiness(hourlyUnits: HourlyUnits, latitude: Double, longitude: Double) =
        with(hourlyUnits) {
            HourlyUnitsEntity(
                latitude = latitude,
                longitude = longitude,
                time = this.time,
                temperature2m = this.temperature2m,
                apparentTemperature = this.apparentTemperature,
                precipitationProbability = this.precipitationProbability,
                weatherCode = this.weatherCode
            )
        }

    fun fromDto(hourlyUnitsEntity: HourlyUnitsEntity): HourlyUnits = with(hourlyUnitsEntity) {
        HourlyUnits(
            time = this.time,
            temperature2m = this.temperature2m,
            apparentTemperature = this.apparentTemperature,
            precipitationProbability = this.precipitationProbability,
            weatherCode = this.weatherCode
        )
    }
}
