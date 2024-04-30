package uz.otamurod.data.database.dao.place

import androidx.room.*
import uz.otamurod.data.database.entity.place.PlaceEntity

@Dao
interface PlaceDao {
    @Upsert
    suspend fun savePlace(place: PlaceEntity)

    @Query("SELECT * FROM ${PlaceEntity.TABLE_NAME} WHERE ${PlaceEntity.ID} = :id")
    suspend fun getPlaceById(id: Int): PlaceEntity

    @Query("SELECT * FROM ${PlaceEntity.TABLE_NAME}")
    suspend fun getAllPlaces(): List<PlaceEntity>

    @Query("DELETE FROM ${PlaceEntity.TABLE_NAME} WHERE ${PlaceEntity.ID} = :id")
    suspend fun deletePlaceById(id: Int)

    @Query("SELECT * FROM ${PlaceEntity.TABLE_NAME} WHERE ${PlaceEntity.LATITUDE} = :latitude AND ${PlaceEntity.LONGITUDE} = :longitude LIMIT 1")
    suspend fun getPlaceByLatLong(latitude: Double, longitude: Double): PlaceEntity?
}
