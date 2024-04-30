package uz.otamurod.data.database.entity.lastlocation

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import uz.otamurod.data.database.entity.lastlocation.LastLocationEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class LastLocationEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = ID)
    var id: Int = 0,

    @ColumnInfo(name = LATITUDE)
    var latitude: Double,

    @ColumnInfo(name = LONGITUDE)
    var longitude: Double,

    @ColumnInfo(name = ADDRESS_NAME)
    var addressName: String,

    @ColumnInfo(name = CORRESPONDING_FORECAST_LATITUDE)
    var correspondingForecastLat: Double,

    @ColumnInfo(name = CORRESPONDING_FORECAST_LONGITUDE)
    var correspondingForecastLong: Double,
) {
    companion object {
        const val TABLE_NAME = "last_location"
        const val ID = "id"
        const val LATITUDE = "latitude"
        const val LONGITUDE = "longitude"
        const val CORRESPONDING_FORECAST_LATITUDE = "corresponding_forecast_latitude"
        const val CORRESPONDING_FORECAST_LONGITUDE = "corresponding_forecast_longitude"
        const val ADDRESS_NAME = "address_name"
    }
}
