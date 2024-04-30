package uz.otamurod.data.network.mapper.weather

import uz.otamurod.data.network.entities.weather.HourlyUnitsResponse as HourlyUnitsDto
import uz.otamurod.domain.api.model.weather.HourlyUnits as HourlyUnitsBo

object HourlyUnitsResponseMapper {
    class HourlyUnits internal constructor(private val dto: HourlyUnitsDto) {
        operator fun invoke(): HourlyUnitsBo = with(dto) {
            HourlyUnitsBo(
                time,
                temperature2m,
                apparentTemperature,
                precipitationProbability,
                weatherCode
            )
        }
    }
}