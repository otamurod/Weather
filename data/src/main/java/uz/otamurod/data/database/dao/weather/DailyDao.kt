package uz.otamurod.data.database.dao.weather

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uz.otamurod.data.database.entity.weather.DailyEntity

@Dao
interface DailyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDaily(daily: DailyEntity)

    @Query("SELECT * FROM ${DailyEntity.TABLE_NAME} WHERE ${DailyEntity.FORECAST_ID} = :forecastId")
    suspend fun getDailyByForecastId(forecastId: Int): List<DailyEntity>?
}