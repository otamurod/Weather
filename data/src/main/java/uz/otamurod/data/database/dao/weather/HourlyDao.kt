package uz.otamurod.data.database.dao.weather

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uz.otamurod.data.database.entity.weather.HourlyEntity

@Dao
interface HourlyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHourly(hourly: HourlyEntity)

    @Query("SELECT * FROM ${HourlyEntity.TABLE_NAME} WHERE ${HourlyEntity.FORECAST_ID} = :forecastId")
    suspend fun getHourlyByForecastId(forecastId: Int): List<HourlyEntity>?
}