package uz.otamurod.data.database.dao.lastlocation

import androidx.room.*
import uz.otamurod.data.database.entity.lastlocation.LastLocationEntity

@Dao
interface LastLocationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLastLocation(lastLocation: LastLocationEntity)

    @Query("SELECT * FROM ${LastLocationEntity.TABLE_NAME} ORDER BY ${LastLocationEntity.ID} DESC LIMIT 1")
    suspend fun getLastLocation(): LastLocationEntity?

    @Update
    suspend fun updateLastLocation(lastLocation: LastLocationEntity)
}
