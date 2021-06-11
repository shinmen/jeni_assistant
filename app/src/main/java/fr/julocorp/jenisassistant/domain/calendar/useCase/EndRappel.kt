package fr.julocorp.jenisassistant.domain.calendar.useCase

import fr.julocorp.jenisassistant.domain.Failure
import fr.julocorp.jenisassistant.domain.GenericActionState
import fr.julocorp.jenisassistant.domain.Success
import fr.julocorp.jenisassistant.domain.calendar.repository.RappelRepository
import fr.julocorp.jenisassistant.infrastructure.common.CoroutineContextProvider
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

class EndRappel @Inject constructor(
    private val rappelRepository: RappelRepository,
    private val coroutineContextProvider: CoroutineContextProvider
) {
    suspend fun handle(rappelId: UUID): GenericActionState = withContext(coroutineContextProvider.iO) {
        try {
            rappelRepository.endRappel(rappelId)
            Success(true)
        } catch (e: Throwable) {
            Failure(e)
        }
    }
}