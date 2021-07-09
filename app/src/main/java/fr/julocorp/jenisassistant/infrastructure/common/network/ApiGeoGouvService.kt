package fr.julocorp.jenisassistant.infrastructure.common.network

import fr.julocorp.jenisassistant.infrastructure.common.repository.apiGeoGouv.dto.ApiGeoGouvFeatureCollection
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiGeoGouvService {
    @GET("/search/")
    suspend fun findByPartialAddress(
        @Query("q") address: String,
        @Query("autocomplete") autocomplete: Int = 1,
        @Query("type") type: String = "housenumber"
    ): ApiGeoGouvFeatureCollection

    companion object {
        const val API_GEO_GOUV_BASE_URL = "https://api-adresse.data.gouv.fr"
    }
}