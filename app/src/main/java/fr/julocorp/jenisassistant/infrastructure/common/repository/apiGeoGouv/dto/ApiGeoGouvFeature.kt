package fr.julocorp.jenisassistant.infrastructure.common.repository.apiGeoGouv.dto
import com.squareup.moshi.Json

data class ApiGeoGouvFeature(
    @field:Json(name="geometry")
    val geometry: ApiGeoGouvGeometry,
    @field:Json(name="properties")
    val properties: ApiGeoGouvAddress,
    @field:Json(name="type")
    val type: String
)