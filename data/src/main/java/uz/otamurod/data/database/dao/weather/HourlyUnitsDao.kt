package uz.otamurod.data.database.dao.weather

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uz.otamurod.data.database.entity.weather.HourlyUnitsEntity

@Dao
interface HourlyUnitsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHourlyUnits(hourlyUnits: HourlyUnitsEntity)

    @Query("SELECT * FROM ${HourlyUnitsEntity.TABLE_NAME} WHERE ${HourlyUnitsEntity.FORECAST_ID} = :forecastId")
    suspend fun getHourlyUnitsByForecastId(forecastId: Int): HourlyUnitsEntity?
}