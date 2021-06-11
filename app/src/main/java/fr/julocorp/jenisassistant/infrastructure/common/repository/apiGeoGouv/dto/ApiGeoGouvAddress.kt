package fr.julocorp.jenisassistant.infrastructure.common.repository.apiGeoGouv.dto

import com.squareup.moshi.Json

data class ApiGeoGouvAddress(
    @field:Json(name = "label") val fullAddress: String,
    @field:Json(name = "type") val type: String,
    @field:Json(name = "houseNumber") val houseNumber: String? = null,
    @field:Json(name = "name") val numberStreet: String,
    @field:Json(name = "postcode") val zipCode: String,
    @field:Json(name = "city") val city: String,
    @field:Json(name = "street") val street: String? = null,
)