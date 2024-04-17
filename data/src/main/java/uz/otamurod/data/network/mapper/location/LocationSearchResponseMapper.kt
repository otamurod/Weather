package uz.otamurod.data.network.mapper.location

import uz.otamurod.data.network.entities.location.LocationSearchResponse as LocationSearchDto
import uz.otamurod.domain.api.model.location.LocationSearch as LocationSearchBo

object LocationSearchResponseMapper {
    class LocationSearch internal constructor(private val dto: LocationSearchDto) {
        operator fun invoke(): LocationSearchBo = with(dto) {
            LocationSearchBo(
                places = places.map {
                    PlaceResponseMapper.Place(it).invoke()
                }
            )
        }
    }
}