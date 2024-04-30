package uz.otamurod.data.database.mapper.place

import uz.otamurod.data.database.entity.place.PlaceEntity
import uz.otamurod.domain.api.model.location.Place

object PlaceDbMapper {
    fun fromBusiness(place: Place) = with(place) {
        PlaceEntity(
            id = this.id,
            name = this.name,
            latitude = this.latitude,
            longitude = this.longitude,
            elevation = this.elevation,
            featureCode = this.featureCode,
            countryCode = this.countryCode,
            admin1Id = this.admin1Id,
            timezone = this.timezone,
            population = this.population,
            countryId = this.countryId,
            country = this.country,
            admin1 = this.admin1,
            correspondingForecastLat = this.correspondingForecastLat,
            correspondingForecastLong = this.correspondingForecastLong
        )
    }

    fun fromDto(placeEntity: PlaceEntity?): Place? = with(placeEntity) {
        if (this != null) {
            return Place(
                id = this.id,
                name = this.name,
                latitude = this.latitude,
                longitude = this.longitude,
                elevation = this.elevation,
                featureCode = this.featureCode,
                countryCode = this.countryCode,
                admin1Id = this.admin1Id,
                timezone = this.timezone,
                population = this.population,
                countryId = this.countryId,
                country = this.country,
                admin1 = this.admin1,
                correspondingForecastLat = this.correspondingForecastLat,
                correspondingForecastLong = this.correspondingForecastLong
            )
        } else {
            null
        }
    }
}
