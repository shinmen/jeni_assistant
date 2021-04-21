package fr.julocorp.jenisassistant.domain.common.repository

import fr.julocorp.jenisassistant.domain.common.FullAddress

interface AdresseSearcher {
    suspend fun findByPartialAddress(address: String): List<FullAddress>

    suspend fun find(address: String): FullAddress?
}