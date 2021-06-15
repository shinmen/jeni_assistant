package fr.julocorp.jenisassistant.domain.calendar.useCase

import fr.julocorp.jenisassistant.domain.common.repository.AdresseSearcher
import fr.julocorp.jenisassistant.infrastructure.CoroutineContextProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchFullAddressPropositionsWithPartialAddress @Inject constructor(
    private val addresseSearcher: AdresseSearcher,
    private val coroutineContextProvider: CoroutineContextProvider,
) {
    suspend fun handle(partialAddress: String) = withContext(coroutineContextProvider.iO) {
        addresseSearcher.findByPartialAddress(partialAddress)
    }
}