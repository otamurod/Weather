package uz.otamurod.domain.model

data class LastLocation(
    val id: Int = 0,
    val latitude: Double,
    val longitude: Double,
    val addressName: String
):java.io.Serializable