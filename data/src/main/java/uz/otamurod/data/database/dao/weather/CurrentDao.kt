package uz.otamurod.data.database.dao.weather

import androidx.room.*
import uz.otamurod.data.database.entity.weather.CurrentEntity

@Dao
interface CurrentDao {
    @Upsert
    suspend fun saveCurrent(current: CurrentEntity)

    @Query("SELECT * FROM ${CurrentEntity.TABLE_NAME} WHERE ${CurrentEntity.FORECAST_LATITUDE} = :latitude AND ${CurrentEntity.FORECAST_LONGITUDE} = :longitude")
    suspend fun getCurrentByForecastId(latitude: Double, longitude: Double): CurrentEntity?


    @Query("DELETE FROM ${CurrentEntity.TABLE_NAME} WHERE ${CurrentEntity.FORECAST_LATITUDE} = :latitude AND ${CurrentEntity.FORECAST_LONGITUDE} = :longitude")
    suspend fun deleteCurrentByForecastId(latitude: Double, longitude: Double)
}