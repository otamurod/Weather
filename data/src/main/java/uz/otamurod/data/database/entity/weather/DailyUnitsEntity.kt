package uz.otamurod.data.database.entity.weather

import androidx.room.ColumnInfo
import androidx.room.Entity
import uz.otamurod.data.database.entity.weather.DailyUnitsEntity.Companion.FORECAST_LATITUDE
import uz.otamurod.data.database.entity.weather.DailyUnitsEntity.Companion.FORECAST_LONGITUDE
import uz.otamurod.data.database.entity.weather.DailyUnitsEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME, primaryKeys = [FORECAST_LATITUDE, FORECAST_LONGITUDE])
data class DailyUnitsEntity(
    @ColumnInfo(name = FORECAST_LATITUDE)
    var latitude: Double,

    @ColumnInfo(name = FORECAST_LONGITUDE)
    var longitude: Double,

    @ColumnInfo(name = TIME)
    var time: String,

    @ColumnInfo(name = WEATHER_CODE)
    var weatherCode: String,

    @ColumnInfo(name = TEMPERATURE_2M_MAX)
    var temperature2mMax: String,

    @ColumnInfo(name = TEMPERATURE_2M_MIN)
    var temperature2mMin: String,

    @ColumnInfo(name = SUNRISE)
    var sunrise: String,

    @ColumnInfo(name = SUNSET)
    var sunset: String,

    @ColumnInfo(name = RAIN_SUM)
    var rainSum: String,

    @ColumnInfo(name = PRECIPITATION_PROBABILITY_MAX)
    var precipitationProbabilityMax: String,

    @ColumnInfo(name = WIND_SPEED_10M_MAX)
    var windSpeed10mMax: String
) {
    companion object {
        const val TABLE_NAME = "daily_units"
        const val FORECAST_LATITUDE = "forecast_latitude"
        const val FORECAST_LONGITUDE = "forecast_longitude"
        const val TIME = "time"
        const val WEATHER_CODE = "weather_code"
        const val TEMPERATURE_2M_MAX = "temperature_2m_Max"
        const val TEMPERATURE_2M_MIN = "temperature_2m_Min"
        const val SUNRISE = "sunrise"
        const val SUNSET = "sunset"
        const val RAIN_SUM = "rainSum"
        const val PRECIPITATION_PROBABILITY_MAX = "precipitation_probability_max"
        const val WIND_SPEED_10M_MAX = "wind_speed_10m_max"
    }
}