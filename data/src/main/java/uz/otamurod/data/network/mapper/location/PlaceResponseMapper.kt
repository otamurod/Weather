package uz.otamurod.data.network.mapper.location

import uz.otamurod.data.network.entities.location.PlaceResponse as PlaceDto
import uz.otamurod.domain.api.model.location.Place as PlaceBo

object PlaceResponseMapper {
    class Place internal constructor(private val dto: PlaceDto) {
        operator fun invoke(): PlaceBo = with(dto) {
            PlaceBo(
                id,
                name,
                latitude,
                longitude,
                elevation,
                featureCode,
                countryCode,
                admin1Id,
                timezone,
                population,
                countryId,
                country,
                admin1
            )
        }
    }
}