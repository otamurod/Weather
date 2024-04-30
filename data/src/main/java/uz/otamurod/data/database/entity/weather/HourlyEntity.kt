package uz.otamurod.data.database.entity.weather

import androidx.room.ColumnInfo
import androidx.room.Entity
import uz.otamurod.data.database.entity.weather.HourlyEntity.Companion.FORECAST_LATITUDE
import uz.otamurod.data.database.entity.weather.HourlyEntity.Companion.FORECAST_LONGITUDE
import uz.otamurod.data.database.entity.weather.HourlyEntity.Companion.ID
import uz.otamurod.data.database.entity.weather.HourlyEntity.Companion.TABLE_NAME
import uz.otamurod.data.database.entity.weather.HourlyEntity.Companion.TIME

@Entity(tableName = TABLE_NAME, primaryKeys = [ID, FORECAST_LATITUDE, FORECAST_LONGITUDE, TIME])
data class HourlyEntity(
    @ColumnInfo(name = ID)
    var id: Int = 0,

    @ColumnInfo(name = FORECAST_LATITUDE)
    var latitude: Double,

    @ColumnInfo(name = FORECAST_LONGITUDE)
    var longitude: Double,

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
        const val FORECAST_LATITUDE = "forecast_latitude"
        const val FORECAST_LONGITUDE = "forecast_longitude"
        const val TIME = "time"
        const val TEMPERATURE_2_M = "temperature_2_m"
        const val APPARENT_TEMPERATURE = "apparent_temperature"
        const val PRECIPITATION_PROBABILITY = "precipitation_probability"
        const val WEATHER_CODE = "weather_code"
    }
}