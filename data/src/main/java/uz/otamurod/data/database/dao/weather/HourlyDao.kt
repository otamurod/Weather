package uz.otamurod.data.database.dao.weather

import androidx.room.*
import uz.otamurod.data.database.entity.weather.HourlyEntity

@Dao
interface HourlyDao {
    @Upsert
    suspend fun saveHourly(hourly: HourlyEntity)

    @Query("SELECT * FROM ${HourlyEntity.TABLE_NAME} WHERE ${HourlyEntity.FORECAST_LATITUDE} = :latitude AND ${HourlyEntity.FORECAST_LONGITUDE} = :longitude")
    suspend fun getHourlyByForecastId(latitude: Double, longitude: Double): List<HourlyEntity>?

    @Query("DELETE FROM ${HourlyEntity.TABLE_NAME} WHERE ${HourlyEntity.FORECAST_LATITUDE} = :latitude AND ${HourlyEntity.FORECAST_LONGITUDE} = :longitude")
    suspend fun deleteHourlyByForecastId(latitude: Double, longitude: Double)
}