package uz.otamurod.data.database.dao.weather

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uz.otamurod.data.database.entity.weather.ForecastEntity

@Dao
interface ForecastDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertForecast(forecast: ForecastEntity): Long

    @Query("SELECT * FROM ${ForecastEntity.TABLE_NAME} WHERE ${ForecastEntity.LATITUDE} = :latitude AND ${ForecastEntity.LONGITUDE} = :longitude")
    suspend fun getForecastByLatLong(latitude: Double, longitude: Double): ForecastEntity?
}
