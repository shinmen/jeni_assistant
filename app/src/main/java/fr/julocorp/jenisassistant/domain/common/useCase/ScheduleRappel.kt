package fr.julocorp.jenisassistant.domain.common.useCase

import fr.julocorp.jenisassistant.domain.Failure
import fr.julocorp.jenisassistant.domain.GenericActionState
import fr.julocorp.jenisassistant.domain.Success
import fr.julocorp.jenisassistant.domain.common.Rappel
import fr.julocorp.jenisassistant.domain.common.repository.RappelRepository
import javax.inject.Inject

class ScheduleRappel @Inject constructor(
    private val rappelRepository: RappelRepository
) {
    suspend fun handle(rappel: Rappel): GenericActionState {
        if (rappel.sujet.isBlank()) {
            return Failure(RappelSujetEmpty())
        }

        rappelRepository.scheduleRappel(rappel)

        return Success(true)
    }
}