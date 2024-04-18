package uz.otamurod.data.database.dao.weather

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uz.otamurod.data.database.entity.weather.CurrentUnitsEntity

@Dao
interface CurrentUnitsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrentUnits(currentUnits: CurrentUnitsEntity)

    @Query("SELECT * FROM ${CurrentUnitsEntity.TABLE_NAME} WHERE ${CurrentUnitsEntity.FORECAST_ID} = :forecastId")
    suspend fun getCurrentUnitsByForecastId(forecastId: Int): CurrentUnitsEntity?
}