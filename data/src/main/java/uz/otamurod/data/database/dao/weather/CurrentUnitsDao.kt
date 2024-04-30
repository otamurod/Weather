package uz.otamurod.data.database.dao.weather

import androidx.room.*
import uz.otamurod.data.database.entity.weather.CurrentUnitsEntity

@Dao
interface CurrentUnitsDao {
    @Upsert
    suspend fun saveCurrentUnits(currentUnits: CurrentUnitsEntity)

    @Query("SELECT * FROM ${CurrentUnitsEntity.TABLE_NAME} WHERE ${CurrentUnitsEntity.FORECAST_LATITUDE} = :latitude AND ${CurrentUnitsEntity.FORECAST_LONGITUDE} = :longitude")
    suspend fun getCurrentUnitsByForecastId(
        latitude: Double,
        longitude: Double
    ): CurrentUnitsEntity?

    @Query("DELETE FROM ${CurrentUnitsEntity.TABLE_NAME} WHERE ${CurrentUnitsEntity.FORECAST_LATITUDE} = :latitude AND ${CurrentUnitsEntity.FORECAST_LONGITUDE} = :longitude")
    suspend fun deleteCurrentUnitsByForecastId(latitude: Double, longitude: Double)
}