package uz.otamurod.data.database.entity.weather

import androidx.room.ColumnInfo
import androidx.room.Entity
import uz.otamurod.data.database.entity.weather.CurrentUnitsEntity.Companion.FORECAST_LATITUDE
import uz.otamurod.data.database.entity.weather.CurrentUnitsEntity.Companion.FORECAST_LONGITUDE
import uz.otamurod.data.database.entity.weather.CurrentUnitsEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME, primaryKeys = [FORECAST_LATITUDE, FORECAST_LONGITUDE])
data class CurrentUnitsEntity(
    @ColumnInfo(name = FORECAST_LATITUDE)
    var latitude: Double,

    @ColumnInfo(name = FORECAST_LONGITUDE)
    var longitude: Double,

    @ColumnInfo(name = TIME)
    var time: String,

    @ColumnInfo(name = INTERVAL)
    var interval: String,

    @ColumnInfo(name = TEMPERATURE_2_M)
    var temperature2m: String,

    @ColumnInfo(name = APPARENT_TEMPERATURE)
    var apparentTemperature: String,

    @ColumnInfo(name = IS_DAY)
    var isDay: String,

    @ColumnInfo(name = WEATHER_CODE)
    var weatherCode: String
) {
    companion object {
        const val TABLE_NAME = "current_units"
        const val FORECAST_LATITUDE = "forecast_latitude"
        const val FORECAST_LONGITUDE = "forecast_longitude"
        const val TIME = "time"
        const val INTERVAL = "interval"
        const val TEMPERATURE_2_M = "temperature_2_m"
        const val APPARENT_TEMPERATURE = "apparent_temperature"
        const val IS_DAY = "is_day"
        const val WEATHER_CODE = "weather_code"
    }
}
