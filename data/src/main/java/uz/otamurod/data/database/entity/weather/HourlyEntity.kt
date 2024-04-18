package uz.otamurod.data.database.entity.weather

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import uz.otamurod.data.database.entity.weather.HourlyEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class HourlyEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    var id: Int = 0,

    @ColumnInfo(name = FORECAST_ID)
    var forecastId: Int,

    @ColumnInfo(name = TIME)
    var time: String,

    @ColumnInfo(name = TEMPERATURE_2_M)
    var temperature2m: Double,

    @ColumnInfo(name = APPARENT_TEMPERATURE)
    var apparentTemperature: Double,

    @ColumnInfo(name = PRECIPITATION_PROBABILITY)
    var precipitationProbability: Int,

    @ColumnInfo(name = WEATHER_CODE)
    var weatherCode: Int
) {
    companion object {
        const val TABLE_NAME = "hourly"
        const val ID = "id"
        const val FORECAST_ID = "forecast_id"
        const val TIME = "time"
        const val TEMPERATURE_2_M = "temperature_2_m"
        const val APPARENT_TEMPERATURE = "apparent_temperature"
        const val PRECIPITATION_PROBABILITY = "precipitation_probability"
        const val WEATHER_CODE = "weather_code"
    }
}