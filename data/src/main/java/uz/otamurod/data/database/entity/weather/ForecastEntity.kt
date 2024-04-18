package uz.otamurod.data.database.entity.weather

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import uz.otamurod.data.database.entity.weather.ForecastEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class ForecastEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    var id: Int = 0,

    @ColumnInfo(name = LATITUDE)
    var latitude: Double,

    @ColumnInfo(name = LONGITUDE)
    var longitude: Double,

    @ColumnInfo(name = GENERATION_TIME_MS)
    var generationTimeMs: Double,

    @ColumnInfo(name = UTC_OFFSET_SECONDS)
    var utcOffsetSeconds: Int,

    @ColumnInfo(name = TIMEZONE)
    var timezone: String,

    @ColumnInfo(name = TIMEZONE_ABBREVIATION)
    var timezoneAbbreviation: String,

    @ColumnInfo(name = ELEVATION)
    var elevation: Double
) {
    companion object {
        const val TABLE_NAME = "forecast"
        const val ID = "id"
        const val LATITUDE = "latitude"
        const val LONGITUDE = "longitude"
        const val GENERATION_TIME_MS = "generation_time_Ms"
        const val UTC_OFFSET_SECONDS = "utc_offset_seconds"
        const val TIMEZONE = "timezone"
        const val TIMEZONE_ABBREVIATION = "timezone_abbreviation"
        const val ELEVATION = "elevation"
    }
}
