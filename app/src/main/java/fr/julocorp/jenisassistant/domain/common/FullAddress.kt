package fr.julocorp.jenisassistant.domain.common

data class FullAddress(
    val numberStreet: String,
    val houseNumber: String?,
    val streetname: String?,
    val zipCode: String,
    val city: String,
    val geolocation: Geolocation
) {
    override fun toString(): String = "$numberStreet $zipCode $city"
        .trim { it.toString().isNullOrBlank() }
        .lowercase()
}