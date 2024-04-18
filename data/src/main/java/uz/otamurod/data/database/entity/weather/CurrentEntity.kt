package uz.otamurod.data.database.entity.weather

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import uz.otamurod.data.database.entity.weather.CurrentEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class CurrentEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = FORECAST_ID)
    var forecastId: Int,

    @ColumnInfo(name = TIME)
    var time: String,

    @ColumnInfo(name = INTERVAL)
    var interval: Int,

    @ColumnInfo(name = TEMPERATURE_2_M)
    var temperature2m: Double,

    @ColumnInfo(name = APPARENT_TEMPERATURE)
    var apparentTemperature: Double,

    @ColumnInfo(name = IS_DAY)
    var isDay: Int,

    @ColumnInfo(name = WEATHER_CODE)
    var weatherCode: Int
) {
    companion object {
        const val TABLE_NAME = "current"
        const val FORECAST_ID = "forecast_id"
        const val TIME = "time"
        const val INTERVAL = "interval"
        const val TEMPERATURE_2_M = "temperature_2_m"
        const val APPARENT_TEMPERATURE = "apparent_temperature"
        const val IS_DAY = "is_day"
        const val WEATHER_CODE = "weather_code"
    }
}