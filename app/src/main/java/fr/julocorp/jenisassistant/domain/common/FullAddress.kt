package fr.julocorp.jenisassistant.domain.common

data class FullAddress(
    val houseNumber: String?,
    val streetname: String,
    val zipCode: String,
    val city: String,
    val district: String?,
    val geolocation: Geolocation
) {
    override fun toString(): String = "$houseNumber $streetname $zipCode ${city.capitalize()}"
}