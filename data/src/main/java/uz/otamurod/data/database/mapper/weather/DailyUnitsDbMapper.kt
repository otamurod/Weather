package uz.otamurod.data.database.mapper.weather

import uz.otamurod.data.database.entity.weather.DailyUnitsEntity
import uz.otamurod.domain.api.model.weather.DailyUnits

object DailyUnitsDbMapper {
    fun fromBusiness(dailyUnits: DailyUnits, latitude: Double, longitude: Double) =
        with(dailyUnits) {
            DailyUnitsEntity(
                latitude = latitude,
                longitude = longitude,
                time = this.time,
                weatherCode = this.weatherCode,
                temperature2mMax = this.temperature2mMax,
                temperature2mMin = this.temperature2mMin,
                sunrise = this.sunrise,
                sunset = this.sunset,
                rainSum = this.rainSum,
                precipitationProbabilityMax = this.precipitationProbabilityMax,
                windSpeed10mMax = this.windSpeed10mMax
            )
        }

    fun fromDto(dailyUnitsEntity: DailyUnitsEntity): DailyUnits = with(dailyUnitsEntity) {
        DailyUnits(
            time = this.time,
            weatherCode = this.weatherCode,
            temperature2mMax = this.temperature2mMax,
            temperature2mMin = this.temperature2mMin,
            sunrise = this.sunrise,
            sunset = this.sunset,
            rainSum = this.rainSum,
            precipitationProbabilityMax = this.precipitationProbabilityMax,
            windSpeed10mMax = this.windSpeed10mMax
        )
    }
}
