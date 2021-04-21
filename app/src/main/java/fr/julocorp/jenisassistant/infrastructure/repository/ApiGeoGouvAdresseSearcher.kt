package fr.julocorp.jenisassistant.infrastructure.repository

import fr.julocorp.jenisassistant.domain.common.FullAddress
import fr.julocorp.jenisassistant.domain.common.Geolocation
import fr.julocorp.jenisassistant.domain.common.repository.AdresseSearcher

class ApiGeoGouvAdresseSearcher : AdresseSearcher {
    override suspend fun find(address: String): FullAddress {
        return FullAddress(
            "10",
            "rue pasteur",
            "33200",
            "Bordeaux",
            null,
            Geolocation(44.85143655984682F, -0.6167830056937137F)
        )
    }

    override suspend fun findByPartialAddress(address: String): List<FullAddress> {
        return listOf(
            FullAddress(
                "10",
                "rue pasteur",
                "33200",
                "Bordeaux",
                null,
                Geolocation(44.85143655984682F, -0.6167830056937137F)
            )
        )
    }
}