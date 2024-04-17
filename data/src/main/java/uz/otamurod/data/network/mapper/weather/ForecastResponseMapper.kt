package uz.otamurod.data.network.mapper.weather

import uz.otamurod.data.network.entities.weather.ForecastResponse as ForecastDto
import uz.otamurod.domain.api.model.weather.Forecast as ForecastBo

object ForecastResponseMapper {
    class Forecast internal constructor(private val dto: ForecastDto) {
        operator fun invoke(): ForecastBo = with(dto) {
            ForecastBo(
                latitude = latitude,
                longitude = longitude,
                generationtimeMs = generationtimeMs,
                utcOffsetSeconds = utcOffsetSeconds,
                timezone = timezone,
                timezoneAbbreviation = timezoneAbbreviation,
                elevation = elevation,
                currentUnits = CurrentUnitsResponseMapper.CurrentUnits(currentUnits).invoke(),
                current = CurrentResponseMapper.Current(current).invoke(),
                hourlyUnits = HourlyUnitsResponseMapper.HourlyUnits(hourlyUnits).invoke(),
                hourly = HourlyResponseMapper.Hourly(hourly).invoke(),
                dailyUnits = DailyUnitsResponseMapper.DailyUnits(dailyUnits).invoke(),
                daily = DailyResponseMapper.Daily(daily).invoke()
            )
        }
    }
}