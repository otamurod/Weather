package uz.otamurod.data.database.entity.place

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import uz.otamurod.data.database.entity.place.PlaceEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class PlaceEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = ID)
    var id: Int = 0,

    @ColumnInfo(name = NAME)
    var name: String,

    @ColumnInfo(name = LATITUDE)
    var latitude: Double,

    @ColumnInfo(name = LONGITUDE)
    var longitude: Double,

    @ColumnInfo(name = ELEVATION)
    var elevation: Double,

    @ColumnInfo(name = FEATURE_CODE)
    var featureCode: String,

    @ColumnInfo(name = COUNTRY_CODE)
    var countryCode: String,

    @ColumnInfo(name = ADMIN1_ID)
    var admin1Id: Int,

    @ColumnInfo(name = TIMEZONE)
    var timezone: String,

    @ColumnInfo(name = POPULATION)
    var population: Int,

    @ColumnInfo(name = COUNTRY_ID)
    var countryId: Int,

    @ColumnInfo(name = COUNTRY)
    var country: String,

    @ColumnInfo(name = ADMIN1)
    var admin1: String,

    @ColumnInfo(name = CORRESPONDING_FORECAST_LATITUDE)
    var correspondingForecastLat: Double,

    @ColumnInfo(name = CORRESPONDING_FORECAST_LONGITUDE)
    var correspondingForecastLong: Double,
) {
    companion object {
        const val TABLE_NAME = "places"
        const val ID = "id"
        const val NAME = "name"
        const val LATITUDE = "latitude"
        const val LONGITUDE = "longitude"
        const val ELEVATION = "elevation"
        const val FEATURE_CODE = "feature_code"
        const val COUNTRY_CODE = "country_code"
        const val ADMIN1_ID = "admin1_Id"
        const val TIMEZONE = "timezone"
        const val POPULATION = "population"
        const val COUNTRY_ID = "country_id"
        const val COUNTRY = "country"
        const val ADMIN1 = "admin1"
        const val CORRESPONDING_FORECAST_LATITUDE = "corresponding_forecast_latitude"
        const val CORRESPONDING_FORECAST_LONGITUDE = "corresponding_forecast_longitude"
    }
}
