package uz.otamurod.domain.model

data class LastLocation(
    var id: Int = 0,
    var latitude: Double,
    var longitude: Double,
    var addressName: String,
    var correspondingForecastLat: Double,
    var correspondingForecastLong: Double,
) : java.io.Serializable