package fr.julocorp.jenisassistant.domain.calendar.useCase

import fr.julocorp.jenisassistant.domain.common.Failure
import fr.julocorp.jenisassistant.domain.common.ActionState
import fr.julocorp.jenisassistant.domain.common.Success
import fr.julocorp.jenisassistant.domain.common.Rappel
import fr.julocorp.jenisassistant.domain.calendar.exception.RappelSujetEmpty
import fr.julocorp.jenisassistant.domain.calendar.repository.RappelRepository
import fr.julocorp.jenisassistant.infrastructure.CoroutineContextProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ScheduleRappel @Inject constructor(
    private val rappelRepository: RappelRepository,
    private val coroutineContextProvider: CoroutineContextProvider
) {
    suspend fun handle(rappel: Rappel): ActionState<Boolean> = withContext(coroutineContextProvider.iO) {
        if (rappel.sujet.isBlank()) {
            Failure(RappelSujetEmpty())
        } else {
            rappelRepository.scheduleRappel(rappel)
            Success(true)
        }
    }
}