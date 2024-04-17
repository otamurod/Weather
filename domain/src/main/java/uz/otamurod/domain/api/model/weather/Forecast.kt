package uz.otamurod.domain.api.model.weather

import java.io.Serializable

data class Forecast(
    val latitude: Double,

    val longitude: Double,

    val generationtimeMs: Double,

    val utcOffsetSeconds: Int,

    val timezone: String,

    val timezoneAbbreviation: String,

    val elevation: Double,

    val currentUnits: CurrentUnits,

    val current: Current,

    val hourlyUnits: HourlyUnits,

    val hourly: Hourly,

    val dailyUnits: DailyUnits,

    val daily: Daily
) : Serializable