package uz.otamurod.data.database.dao.place

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uz.otamurod.data.database.entity.place.PlaceEntity

@Dao
interface PlaceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlace(place: PlaceEntity)

    @Query("SELECT * FROM ${PlaceEntity.TABLE_NAME} WHERE ${PlaceEntity.ID} = :id")
    suspend fun getPlaceById(id: Int): PlaceEntity

    @Query("SELECT * FROM ${PlaceEntity.TABLE_NAME}")
    suspend fun getAllPlaces(): List<PlaceEntity>

    @Query("DELETE FROM ${PlaceEntity.TABLE_NAME} WHERE ${PlaceEntity.ID} = :id")
    suspend fun deletePlaceById(id: Int)
}
