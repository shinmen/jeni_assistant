package fr.julocorp.jenisassistant.infrastructure.repository

import fr.julocorp.jenisassistant.domain.common.FullAddress
import fr.julocorp.jenisassistant.domain.common.Geolocation
import fr.julocorp.jenisassistant.domain.common.repository.AdresseSearcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class ApiGeoGouvAdresseSearcher : AdresseSearcher {
    override suspend fun findByPartialAddress(address: String): Flow<List<FullAddress>> {
        return MutableStateFlow(address)
            .debounce(DEBOUNCE_TIME_REQUEST)
            .filter { it.isNotBlank() }
            .distinctUntilChanged()
            .flatMapLatest {
                flowOf(
                    listOf(
                        FullAddress(
                            "10",
                            "rue pasteur",
                            "33200",
                            "Bordeaux",
                            null,
                            Geolocation(44.85143655984682F, -0.6167830056937137F)
                        )
                    )
                )
            }
            .flowOn(Dispatchers.IO)
    }

    companion object {
        private const val DEBOUNCE_TIME_REQUEST = 500L
    }
}