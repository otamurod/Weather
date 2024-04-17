package uz.otamurod.data.network.entities.weather

import com.google.gson.annotations.SerializedName

data class ForecastResponse(

    @SerializedName("latitude")
    val latitude: Double,

    @SerializedName("longitude")
    val longitude: Double,

    @SerializedName("generationtime_ms")
    val generationtimeMs: Double,

    @SerializedName("utc_offset_seconds")
    val utcOffsetSeconds: Int,

    @SerializedName("timezone")
    val timezone: String,

    @SerializedName("timezone_abbreviation")
    val timezoneAbbreviation: String,

    @SerializedName("elevation")
    val elevation: Double,

    @SerializedName("current_units")
    val currentUnits: CurrentUnitsResponse,

    @SerializedName("current")
    val current: CurrentResponse,

    @SerializedName("hourly_units")
    val hourlyUnits: HourlyUnitsResponse,

    @SerializedName("hourly")
    val hourly: HourlyResponse,

    @SerializedName("daily_units")
    val dailyUnits: DailyUnitsResponse,

    @SerializedName("daily")
    val daily: DailyResponse
)