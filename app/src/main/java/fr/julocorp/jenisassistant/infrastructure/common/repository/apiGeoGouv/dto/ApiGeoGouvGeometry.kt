package fr.julocorp.jenisassistant.infrastructure.common.repository.apiGeoGouv.dto
import com.squareup.moshi.Json

data class ApiGeoGouvGeometry(
    @field:Json(name="coordinates") val coordinates: List<Double>,
)