package fr.julocorp.jenisassistant.domain.calendar.useCase

import fr.julocorp.jenisassistant.domain.common.Failure
import fr.julocorp.jenisassistant.domain.common.ActionState
import fr.julocorp.jenisassistant.domain.common.Success
import fr.julocorp.jenisassistant.domain.calendar.repository.RappelRepository
import fr.julocorp.jenisassistant.infrastructure.CoroutineContextProvider
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

class EndRappel @Inject constructor(
    private val rappelRepository: RappelRepository,
    private val coroutineContextProvider: CoroutineContextProvider
) {
    suspend fun handle(rappelId: UUID): ActionState<Boolean> = withContext(coroutineContextProvider.iO) {
        try {
            rappelRepository.endRappel(rappelId)
            Success(true)
        } catch (e: Throwable) {
            Failure(e)
        }
    }
}