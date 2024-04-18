package uz.otamurod.data.database.dao.weather

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uz.otamurod.data.database.entity.weather.CurrentEntity

@Dao
interface CurrentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrent(current: CurrentEntity)

    @Query("SELECT * FROM ${CurrentEntity.TABLE_NAME} WHERE ${CurrentEntity.FORECAST_ID} = :forecastId")
    suspend fun getCurrentByForecastId(forecastId: Int): CurrentEntity?
}