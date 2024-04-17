package uz.otamurod.data.network.mapper.weather

import uz.otamurod.data.network.entities.weather.CurrentResponse as CurrentDto
import uz.otamurod.domain.api.model.weather.Current as CurrentBo

object CurrentResponseMapper {
    class Current internal constructor(private val dto: CurrentDto) {
        operator fun invoke(): CurrentBo = with(dto) {
            CurrentBo(time, interval, temperature2m, apparentTemperature, isDay, weatherCode)
        }
    }
}