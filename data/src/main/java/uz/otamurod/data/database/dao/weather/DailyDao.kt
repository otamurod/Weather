package uz.otamurod.data.database.dao.weather

import androidx.room.*
import uz.otamurod.data.database.entity.weather.DailyEntity

@Dao
interface DailyDao {
    @Upsert
    suspend fun saveDaily(daily: DailyEntity)

    @Query("SELECT * FROM ${DailyEntity.TABLE_NAME} WHERE ${DailyEntity.FORECAST_LATITUDE} = :latitude AND ${DailyEntity.FORECAST_LONGITUDE} = :longitude")
    suspend fun getDailyByForecastId(latitude: Double, longitude: Double): List<DailyEntity>?

    @Query("DELETE FROM ${DailyEntity.TABLE_NAME} WHERE ${DailyEntity.FORECAST_LATITUDE} = :latitude AND ${DailyEntity.FORECAST_LONGITUDE} = :longitude")
    suspend fun deleteDailyByForecastId(latitude: Double, longitude: Double)
}