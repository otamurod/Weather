package uz.otamurod.data.network.mapper.weather

import uz.otamurod.data.network.entities.weather.DailyUnitsResponse as DailyUnitsDto
import uz.otamurod.domain.api.model.weather.DailyUnits as DailyUnitsBo

object DailyUnitsResponseMapper {
    class DailyUnits internal constructor(private val dto: DailyUnitsDto) {
        operator fun invoke(): DailyUnitsBo = with(dto) {
            DailyUnitsBo(
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