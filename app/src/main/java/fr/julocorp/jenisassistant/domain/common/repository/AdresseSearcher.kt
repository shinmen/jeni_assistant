package fr.julocorp.jenisassistant.domain.common.repository

import fr.julocorp.jenisassistant.domain.common.FullAddress
import kotlinx.coroutines.flow.Flow

interface AdresseSearcher {
    suspend fun findByPartialAddress(address: String): Flow<List<FullAddress>>
}