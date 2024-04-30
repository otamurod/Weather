package uz.otamurod.data.network.mapper.weather

import uz.otamurod.data.network.entities.weather.DailyResponse as DailyDto
import uz.otamurod.domain.api.model.weather.Daily as DailyBo

object DailyResponseMapper {
    class Daily internal constructor(private val dto: DailyDto) {
        operator fun invoke(): DailyBo = with(dto) {
            DailyBo(
                time,
                weatherCode,
                temperature2mMax,
                temperature2mMin,
                sunrise,
                sunset,
                rainSum,
                precipitationProbabilityMax,
                windSpeed10mMax
            )
        }
    }
}