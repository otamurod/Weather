package uz.otamurod.data.database.mapper.weather

import uz.otamurod.data.database.entity.weather.HourlyEntity
import uz.otamurod.domain.api.model.weather.Hourly

object HourlyDbMapper {
    fun fromBusiness(hourly: Hourly, latitude: Double, longitude: Double) = with(hourly) {
        val hourlyEntities = mutableListOf<HourlyEntity>()

        for (i in this.time.indices) {
            val entity = HourlyEntity(
                latitude = latitude,
                longitude = longitude,
                time = this.time[i],
                temperature2m = this.temperature2m[i],
                apparentTemperature = this.apparentTemperature[i],
                precipitationProbability = this.precipitationProbability[i],
                weatherCode = this.weatherCode[i]
            )
            hourlyEntities.add(entity)
        }
        hourlyEntities
    }

    fun fromDto(hourlyEntityList: List<HourlyEntity>): Hourly = with(hourlyEntityList) {
        val timeList = mutableListOf<String>()
        val temperature2mList = mutableListOf<Double>()
        val apparentTemperatureList = mutableListOf<Double>()
        val precipitationProbabilityList = mutableListOf<Int>()
        val weatherCodeList = mutableListOf<Int>()

        for (entity in this) {
            timeList.add(entity.time)
            temperature2mList.add(entity.temperature2m)
            apparentTemperatureList.add(entity.apparentTemperature)
            precipitationProbabilityList.add(entity.precipitationProbability)
            weatherCodeList.add(entity.weatherCode)
        }

        Hourly(
            timeList,
            temperature2mList,
            apparentTemperatureList,
            precipitationProbabilityList,
            weatherCodeList
        )
    }
}
