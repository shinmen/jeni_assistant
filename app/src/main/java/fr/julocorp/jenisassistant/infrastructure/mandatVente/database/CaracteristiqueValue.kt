package fr.julocorp.jenisassistant.infrastructure.mandatVente.database

import com.squareup.moshi.Json

data class CaracteristiqueValue(
    @field:Json(name = "type") val type: CaracteristiqueValueType,
    @field:Json(name = "value") val value: String
)