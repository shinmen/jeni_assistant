package fr.julocorp.jenisassistant.infrastructure.common.repository.apiGeoGouv

import fr.julocorp.jenisassistant.domain.common.FullAddress
import fr.julocorp.jenisassistant.domain.common.Geolocation
import fr.julocorp.jenisassistant.domain.common.repository.AdresseSearcher
import fr.julocorp.jenisassistant.infrastructure.common.network.ApiGeoGouvService
import javax.inject.Inject

class ApiGeoGouvAdresseSearcher @Inject constructor(
    private val apiGeoGouvService: ApiGeoGouvService,
) : AdresseSearcher {
//    override suspend fun findByPartialAddress(address: String): Flow<List<FullAddress>> =
//        MutableStateFlow(address)
//            .debounce(DEBOUNCE_TIME_REQUEST)
//            .filter { it.isNotBlank() }
//            .distinctUntilChanged()
//            .mapLatest { addressQuery -> addressQuery }
//            .map { addressQuery ->
//                apiGeoGouvService.findByPartialAddress(addressQuery)
//                    .features
//                    .map { feature ->
//                        FullAddress(
//                            feature.properties.numberStreet,
//                            feature.properties.houseNumber,
//                            feature.properties.street,
//                            feature.properties.zipCode,
//                            feature.properties.city,
//                            Geolocation(
//                                feature.geometry.coordinates.first(),
//                                feature.geometry.coordinates.last()
//                            )
//                        )
//                    }
//            }
//            .flowOn(Dispatchers.IO)

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
                        feature.geometry.coordinates.first(),
                        feature.geometry.coordinates.last()
                    )
                )
            }

    companion object {
        private const val DEBOUNCE_TIME_REQUEST = 8000L
        const val API_GEO_GOUV_BASE_URL = "https://api-adresse.data.gouv.fr"
    }
}