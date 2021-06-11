package fr.julocorp.jenisassistant.infrastructure.common.repository.apiGeoGouv

import fr.julocorp.jenisassistant.domain.common.FullAddress
import fr.julocorp.jenisassistant.domain.common.Geolocation
import fr.julocorp.jenisassistant.domain.common.repository.AdresseSearcher
import fr.julocorp.jenisassistant.infrastructure.common.CoroutineContextProvider
import fr.julocorp.jenisassistant.infrastructure.common.network.ApiGeoGouvService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class ApiGeoGouvAdresseSearcher @Inject constructor(
    private val apiGeoGouvService: ApiGeoGouvService,
    private val coroutineContextProvider: CoroutineContextProvider
) : AdresseSearcher {
    override suspend fun findByPartialAddress(address: String): Flow<List<FullAddress>> =
        MutableStateFlow(address)
            .debounce(DEBOUNCE_TIME_REQUEST)
            .filter { it.isNotBlank() }
            .distinctUntilChanged()
            .flatMapLatest { addressQuery ->
                flowOf(
                    apiGeoGouvService.findByPartialAddress(addressQuery)
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
                )
            }
            .flowOn(Dispatchers.IO)

    companion object {
        private const val DEBOUNCE_TIME_REQUEST = 500L
        const val API_GEO_GOUV_BASE_URL = "https://api-adresse.data.gouv.fr"
    }
}