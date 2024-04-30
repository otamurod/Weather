package uz.otamurod.data.database.dao.weather

import androidx.room.*
import uz.otamurod.data.database.entity.weather.ForecastEntity

@Dao
interface ForecastDao {
    @Upsert
    suspend fun saveForecast(forecast: ForecastEntity): Long

    @Query("SELECT * FROM ${ForecastEntity.TABLE_NAME} WHERE ${ForecastEntity.LATITUDE} = :latitude AND ${ForecastEntity.LONGITUDE} = :longitude")
    suspend fun getForecastByLatLong(latitude: Double, longitude: Double): ForecastEntity?

    @Query("DELETE FROM ${ForecastEntity.TABLE_NAME} WHERE ${ForecastEntity.LATITUDE} = :latitude AND ${ForecastEntity.LONGITUDE} = :longitude")
    suspend fun deleteForecast(latitude: Double, longitude: Double)
}
