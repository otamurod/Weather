package uz.otamurod.data.database.dao.weather

import androidx.room.*
import uz.otamurod.data.database.entity.weather.HourlyUnitsEntity

@Dao
interface HourlyUnitsDao {
    @Upsert
    suspend fun saveHourlyUnits(hourlyUnits: HourlyUnitsEntity)

    @Query("SELECT * FROM ${HourlyUnitsEntity.TABLE_NAME} WHERE ${HourlyUnitsEntity.FORECAST_LATITUDE} = :latitude AND ${HourlyUnitsEntity.FORECAST_LONGITUDE} = :longitude")
    suspend fun getHourlyUnitsByForecastId(latitude: Double, longitude: Double): HourlyUnitsEntity?

    @Query("DELETE FROM ${HourlyUnitsEntity.TABLE_NAME} WHERE ${HourlyUnitsEntity.FORECAST_LATITUDE} = :latitude AND ${HourlyUnitsEntity.FORECAST_LONGITUDE} = :longitude")
    suspend fun deleteHourlyUnitsByForecastId(latitude: Double, longitude: Double)
}