package fr.julocorp.jenisassistant.infrastructure.common.repository.apiGeoGouv.dto

import com.squareup.moshi.Json

data class ApiGeoGouvFeatureCollection(
    @field:Json(name="features") val features: List<ApiGeoGouvFeature>
)