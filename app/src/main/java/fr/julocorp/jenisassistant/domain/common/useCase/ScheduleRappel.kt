package fr.julocorp.jenisassistant.domain.common.useCase

import fr.julocorp.jenisassistant.domain.Failure
import fr.julocorp.jenisassistant.domain.GenericActionState
import fr.julocorp.jenisassistant.domain.Success
import fr.julocorp.jenisassistant.domain.common.Rappel
import fr.julocorp.jenisassistant.domain.common.repository.RappelRepository
import fr.julocorp.jenisassistant.infrastructure.common.CoroutineContextProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ScheduleRappel @Inject constructor(
    private val rappelRepository: RappelRepository,
    private val coroutineContextProvider: CoroutineContextProvider
) {
    suspend fun handle(rappel: Rappel): GenericActionState  = withContext(coroutineContextProvider.iO) {
        if (rappel.sujet.isBlank()) {
            Failure(RappelSujetEmpty())
        } else {
            rappelRepository.scheduleRappel(rappel)
            Success(true)
        }
    }
}