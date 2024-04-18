package uz.otamurod.data.database.dao.weather

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uz.otamurod.data.database.entity.weather.DailyUnitsEntity

@Dao
interface DailyUnitsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDailyUnits(dailyUnits: DailyUnitsEntity)

    @Query("SELECT * FROM ${DailyUnitsEntity.TABLE_NAME} WHERE ${DailyUnitsEntity.FORECAST_ID} = :forecastId")
    suspend fun getDailyUnitsByForecastId(forecastId: Int): DailyUnitsEntity?
}