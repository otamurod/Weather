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
    suspend fun insertLastLocation(lastLocation: LastLocationEntity) =
        lastLocationDao.insertLastLocation(lastLocation)

    suspend fun getLastLocation(): LastLocationEntity = lastLocationDao.getLastLocation()

    suspend fun updateLastLocation(lastLocation: LastLocationEntity) =
        lastLocationDao.updateLastLocation(lastLocation)

    // Searched Location
    suspend fun insertPlace(place: PlaceEntity) = placeDao.insertPlace(place)

    suspend fun getPlaceById(id: Int): PlaceEntity = placeDao.getPlaceById(id)

    suspend fun getAllPlaces(): List<PlaceEntity> = placeDao.getAllPlaces()

    suspend fun deletePlaceById(id: Int) = placeDao.deletePlaceById(id)

    // Weather
    suspend fun insertCurrent(current: CurrentEntity) = currentDao.insertCurrent(current)

    suspend fun getCurrentByForecastId(forecastId: Int): CurrentEntity? =
        currentDao.getCurrentByForecastId(forecastId)

    suspend fun insertCurrentUnits(currentUnits: CurrentUnitsEntity) =
        currentUnitsDao.insertCurrentUnits(currentUnits)

    suspend fun getCurrentUnitsByForecastId(forecastId: Int): CurrentUnitsEntity? =
        currentUnitsDao.getCurrentUnitsByForecastId(forecastId)

    suspend fun insertDaily(daily: DailyEntity) = dailyDao.insertDaily(daily)

    suspend fun getDailyByForecastId(forecastId: Int): List<DailyEntity>? =
        dailyDao.getDailyByForecastId(forecastId)

    suspend fun insertDailyUnits(dailyUnits: DailyUnitsEntity) =
        dailyUnitsDao.insertDailyUnits(dailyUnits)

    suspend fun getDailyUnitsByForecastId(forecastId: Int): DailyUnitsEntity? =
        dailyUnitsDao.getDailyUnitsByForecastId(forecastId)

    suspend fun insertHourly(hourly: HourlyEntity) = hourlyDao.insertHourly(hourly)

    suspend fun getHourlyByForecastId(forecastId: Int): List<HourlyEntity>? =
        hourlyDao.getHourlyByForecastId(forecastId)

    suspend fun insertHourlyUnits(hourlyUnits: HourlyUnitsEntity) =
        hourlyUnitsDao.insertHourlyUnits(hourlyUnits)

    suspend fun getHourlyUnitsByForecastId(forecastId: Int): HourlyUnitsEntity? =
        hourlyUnitsDao.getHourlyUnitsByForecastId(forecastId)

    suspend fun insertForecast(forecast: ForecastEntity): Int = forecastDao.insertForecast(forecast).toInt()

    suspend fun getForecastByLatLong(latitude: Double, longitude: Double): ForecastEntity? =
        forecastDao.getForecastByLatLong(latitude, longitude)

}