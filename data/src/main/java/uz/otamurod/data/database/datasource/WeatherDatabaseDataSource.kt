package uz.otamurod.data.database.datasource

import uz.otamurod.data.database.dao.lastlocation.LastLocationDao
import uz.otamurod.data.database.dao.place.PlaceDao
import uz.otamurod.data.database.dao.weather.*
import uz.otamurod.data.database.entity.lastlocation.LastLocationEntity
import uz.otamurod.data.database.entity.place.PlaceEntity
import uz.otamurod.data.database.entity.weather.*
import javax.inject.Inject

class WeatherDatabaseDataSource @Inject constructor(
    private val lastLocationDao: LastLocationDao,
    private val placeDao: PlaceDao,
    private val currentDao: CurrentDao,
    private val currentUnitsDao: CurrentUnitsDao,
    private val dailyDao: DailyDao,
    private val dailyUnitsDao: DailyUnitsDao,
    private val hourlyDao: HourlyDao,
    private val hourlyUnitsDao: HourlyUnitsDao,
    private val forecastDao: ForecastDao
) {
    // Device Location

    suspend fun saveLastLocation(lastLocation: LastLocationEntity) =
        lastLocationDao.saveLastLocation(lastLocation)

    suspend fun getLastLocation(): LastLocationEntity? = lastLocationDao.getLastLocation()

    // Searched Location
    suspend fun savePlace(place: PlaceEntity) = placeDao.savePlace(place)

    suspend fun getPlaceById(id: Int): PlaceEntity = placeDao.getPlaceById(id)

    suspend fun getPlaceByLatLong(latitude: Double, longitude: Double): PlaceEntity? =
        placeDao.getPlaceByLatLong(latitude, longitude)

    suspend fun getAllPlaces(): List<PlaceEntity> = placeDao.getAllPlaces()

    suspend fun deletePlaceById(id: Int) = placeDao.deletePlaceById(id)

    // Weather

    suspend fun saveCurrent(current: CurrentEntity) = currentDao.saveCurrent(current)

    suspend fun getCurrentByForecastId(latitude: Double, longitude: Double): CurrentEntity? =
        currentDao.getCurrentByForecastId(latitude, longitude)

    suspend fun saveCurrentUnits(currentUnits: CurrentUnitsEntity) =
        currentUnitsDao.saveCurrentUnits(currentUnits)

    suspend fun getCurrentUnitsByForecastId(
        latitude: Double,
        longitude: Double
    ): CurrentUnitsEntity? =
        currentUnitsDao.getCurrentUnitsByForecastId(latitude, longitude)

    suspend fun saveDaily(daily: DailyEntity) = dailyDao.saveDaily(daily)

    suspend fun getDailyByForecastId(latitude: Double, longitude: Double): List<DailyEntity>? =
        dailyDao.getDailyByForecastId(latitude, longitude)

    suspend fun saveDailyUnits(dailyUnits: DailyUnitsEntity) =
        dailyUnitsDao.saveDailyUnits(dailyUnits)

    suspend fun getDailyUnitsByForecastId(latitude: Double, longitude: Double): DailyUnitsEntity? =
        dailyUnitsDao.getDailyUnitsByForecastId(latitude, longitude)

    suspend fun saveHourly(hourly: HourlyEntity) = hourlyDao.saveHourly(hourly)

    suspend fun getHourlyByForecastId(latitude: Double, longitude: Double): List<HourlyEntity>? =
        hourlyDao.getHourlyByForecastId(latitude, longitude)

    suspend fun saveHourlyUnits(hourlyUnits: HourlyUnitsEntity) =
        hourlyUnitsDao.saveHourlyUnits(hourlyUnits)

    suspend fun getHourlyUnitsByForecastId(
        latitude: Double,
        longitude: Double
    ): HourlyUnitsEntity? =
        hourlyUnitsDao.getHourlyUnitsByForecastId(latitude, longitude)

    suspend fun saveForecast(forecast: ForecastEntity): Int =
        forecastDao.saveForecast(forecast).toInt()

    suspend fun getForecastByLatLong(latitude: Double, longitude: Double): ForecastEntity? =
        forecastDao.getForecastByLatLong(latitude, longitude)

    suspend fun deleteForecast(latitude: Double, longitude: Double) {
        forecastDao.deleteForecast(latitude, longitude)
        currentUnitsDao.deleteCurrentUnitsByForecastId(latitude, longitude)
        currentDao.deleteCurrentByForecastId(latitude, longitude)
        dailyUnitsDao.deleteDailyUnitsByForecastId(latitude, longitude)
        dailyDao.deleteDailyByForecastId(latitude, longitude)
        hourlyUnitsDao.deleteHourlyUnitsByForecastId(latitude, longitude)
        hourlyDao.deleteHourlyByForecastId(latitude, longitude)
    }

}