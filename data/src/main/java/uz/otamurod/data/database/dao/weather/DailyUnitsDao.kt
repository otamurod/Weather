package uz.otamurod.data.database.dao.weather

import androidx.room.*
import uz.otamurod.data.database.entity.weather.DailyUnitsEntity

@Dao
interface DailyUnitsDao {
    @Upsert
    suspend fun saveDailyUnits(dailyUnits: DailyUnitsEntity)

    @Query("SELECT * FROM ${DailyUnitsEntity.TABLE_NAME} WHERE ${DailyUnitsEntity.FORECAST_LATITUDE} = :latitude AND ${DailyUnitsEntity.FORECAST_LONGITUDE} = :longitude")
    suspend fun getDailyUnitsByForecastId(latitude: Double, longitude: Double): DailyUnitsEntity?

    @Query("DELETE FROM ${DailyUnitsEntity.TABLE_NAME} WHERE ${DailyUnitsEntity.FORECAST_LATITUDE} = :latitude AND ${DailyUnitsEntity.FORECAST_LONGITUDE} = :longitude")
    suspend fun deleteDailyUnitsByForecastId(latitude: Double, longitude: Double)
}