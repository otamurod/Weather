package uz.otamurod.data.database.mapper.weather

import uz.otamurod.data.database.entity.weather.*
import uz.otamurod.domain.api.model.weather.Forecast

object ForecastDbMapper {
    fun fromBusiness(forecast: Forecast): ForecastEntity {
        return ForecastEntity(
            latitude = forecast.latitude,
            longitude = forecast.longitude,
            generationTimeMs = forecast.generationTimeMs,
            utcOffsetSeconds = forecast.utcOffsetSeconds,
            timezone = forecast.timezone,
            timezoneAbbreviation = forecast.timezoneAbbreviation,
            elevation = forecast.elevation
        )
    }

    fun fromDto(
        forecastEntity: ForecastEntity,
        currentUnitsEntity: CurrentUnitsEntity,
        currentEntity: CurrentEntity,
        hourlyUnitsEntity: HourlyUnitsEntity,
        hourlyEntity: List<HourlyEntity>,
        dailyUnitsEntity: DailyUnitsEntity,
        dailyEntity: List<DailyEntity>
    ): Forecast = with(forecastEntity) {
        Forecast(
            latitude = this.latitude,
            longitude = this.longitude,
            generationTimeMs = this.generationTimeMs,
            utcOffsetSeconds = this.utcOffsetSeconds,
            timezone = this.timezone,
            timezoneAbbreviation = this.timezoneAbbreviation,
            elevation = this.elevation,
            currentUnits = CurrentUnitsDbMapper.fromDto(currentUnitsEntity),
            current = CurrentDbMapper.fromDto(currentEntity),
            hourlyUnits = HourlyUnitsDbMapper.fromDto(hourlyUnitsEntity),
            hourly = HourlyDbMapper.fromDto(hourlyEntity),
            dailyUnits = DailyUnitsDbMapper.fromDto(dailyUnitsEntity),
            daily = DailyDbMapper.fromDto(dailyEntity)
        )
    }
}
