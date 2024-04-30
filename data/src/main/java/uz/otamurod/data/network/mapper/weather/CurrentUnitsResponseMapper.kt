package uz.otamurod.data.network.mapper.weather

import uz.otamurod.data.network.entities.weather.CurrentUnitsResponse as CurrentUnitsDto
import uz.otamurod.domain.api.model.weather.CurrentUnits as CurrentUnitsBo

object CurrentUnitsResponseMapper {
    class CurrentUnits internal constructor(private val dto: CurrentUnitsDto) {
        operator fun invoke(): CurrentUnitsBo = with(dto) {
            CurrentUnitsBo(time, interval, temperature2m, apparentTemperature, isDay, weatherCode)
        }
    }
}