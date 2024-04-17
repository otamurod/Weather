package uz.otamurod.data.network.mapper.weather

import uz.otamurod.data.network.entities.weather.HourlyResponse as HourlyDto
import uz.otamurod.domain.api.model.weather.Hourly as HourlyBo

object HourlyResponseMapper {
    class Hourly internal constructor(private val dto: HourlyDto) {
        operator fun invoke(): HourlyBo = with(dto) {
            HourlyBo(
                time, temperature2m, apparentTemperature, precipitationProbability, weatherCode
            )
        }
    }
}