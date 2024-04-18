package uz.otamurod.data.database.mapper.weather

import uz.otamurod.data.database.entity.weather.DailyEntity
import uz.otamurod.domain.api.model.weather.Daily

object DailyDbMapper {
    fun fromBusiness(daily: Daily, forecastId: Int) = with(daily) {
        val dailyEntities = mutableListOf<DailyEntity>()

        for (i in this.time.indices) {
            val entity = DailyEntity(
                forecastId = forecastId,
                time = this.time[i],
                weatherCode = this.weatherCode[i],
                temperature2mMax = this.temperature2mMax[i],
                temperature2mMin = this.temperature2mMin[i],
                sunrise = this.sunrise[i],
                sunset = this.sunset[i],
                rainSum = this.rainSum[i],
                precipitationProbabilityMax = this.precipitationProbabilityMax[i],
                windSpeed10mMax = this.windSpeed10mMax[i]
            )
            dailyEntities.add(entity)
        }
        dailyEntities
    }

    fun fromDto(dailyEntityList: List<DailyEntity>): Daily = with(dailyEntityList) {
        val timeList = mutableListOf<String>()
        val weatherCodeList = mutableListOf<Int>()
        val temperature2mMaxList = mutableListOf<Double>()
        val temperature2mMinList = mutableListOf<Double>()
        val sunriseList = mutableListOf<String>()
        val sunsetList = mutableListOf<String>()
        val rainSumList = mutableListOf<Double>()
        val precipitationProbabilityMaxList = mutableListOf<Int>()
        val windSpeed10mMaxList = mutableListOf<Double>()

        for (entity in this) {
            timeList.add(entity.time)
            weatherCodeList.add(entity.weatherCode)
            temperature2mMaxList.add(entity.temperature2mMax)
            temperature2mMinList.add(entity.temperature2mMin)
            sunriseList.add(entity.sunrise)
            sunsetList.add(entity.sunset)
            rainSumList.add(entity.rainSum)
            precipitationProbabilityMaxList.add(entity.precipitationProbabilityMax)
            windSpeed10mMaxList.add(entity.windSpeed10mMax)
        }

        Daily(
            timeList,
            weatherCodeList,
            temperature2mMaxList,
            temperature2mMinList,
            sunriseList,
            sunsetList,
            rainSumList,
            precipitationProbabilityMaxList,
            windSpeed10mMaxList
        )
    }
}
