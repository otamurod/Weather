package uz.otamurod.data.database.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uz.otamurod.data.database.dao.lastlocation.LastLocationDao
import uz.otamurod.data.database.dao.place.PlaceDao
import uz.otamurod.data.database.dao.weather.*
import uz.otamurod.data.database.entity.lastlocation.LastLocationEntity
import uz.otamurod.data.database.entity.place.PlaceEntity
import uz.otamurod.data.database.entity.weather.*

@Database(
    entities = [
        LastLocationEntity::class,
        PlaceEntity::class,
        CurrentEntity::class,
        CurrentUnitsEntity::class,
        DailyEntity::class,
        DailyUnitsEntity::class,
        HourlyEntity::class,
        HourlyUnitsEntity::class,
        ForecastEntity::class],
    version = 1, exportSchema = false
)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun lastLocationDao(): LastLocationDao
    abstract fun placeDao(): PlaceDao
    abstract fun currentDao(): CurrentDao
    abstract fun currentUnitsDao(): CurrentUnitsDao
    abstract fun dailyDao(): DailyDao
    abstract fun dailyUnitsDao(): DailyUnitsDao
    abstract fun hourlyDao(): HourlyDao
    abstract fun hourlyUnitsDao(): HourlyUnitsDao
    abstract fun forecastDao(): ForecastDao

    companion object {
        private var INSTANCE: WeatherDatabase? = null
        private const val DATABASE_NAME = "WEATHER_DATABASE.db"

        @Synchronized
        fun getInstance(context: Context): WeatherDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context, WeatherDatabase::class.java, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build()
            }
            return INSTANCE!!
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}