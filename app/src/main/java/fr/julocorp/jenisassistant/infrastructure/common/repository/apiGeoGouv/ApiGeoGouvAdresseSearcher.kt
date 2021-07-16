package fr.julocorp.jenisassistant.infrastructure.common.repository.apiGeoGouv

import fr.julocorp.jenisassistant.domain.common.FullAddress
import fr.julocorp.jenisassistant.domain.common.Geolocation
import fr.julocorp.jenisassistant.domain.common.repository.AdresseSearcher
import fr.julocorp.jenisassistant.infrastructure.common.network.ApiGeoGouvService
import javax.inject.Inject

class ApiGeoGouvAdresseSearcher @Inject constructor(
    private val apiGeoGouvService: ApiGeoGouvService,
) : AdresseSearcher {
    override suspend fun findByPartialAddress(address: String): List<FullAddress> =
        apiGeoGouvService.findByPartialAddress(address)
            .features
            .map { feature ->
                FullAddress(
                    feature.properties.numberStreet,
                    feature.properties.houseNumber,
                    feature.properties.street,
                    feature.properties.zipCode,
                    feature.properties.city,
                    Geolocation(
                        longitude = feature.geometry.coordinates.first(),
                        latitude = feature.geometry.coordinates.last()
                    )
                )
            }
}