package fr.julocorp.jenisassistant.domain.common.useCase

import fr.julocorp.jenisassistant.domain.Result
import fr.julocorp.jenisassistant.domain.Success
import fr.julocorp.jenisassistant.domain.common.Rappel
import fr.julocorp.jenisassistant.domain.common.repository.RappelRepository
import javax.inject.Inject

class ScheduleRappel @Inject constructor(private val rappelRepository: RappelRepository) {
    fun handle(rappel: Rappel) : Result {

        rappelRepository.scheduleRappel(rappel)

        return Success(true)
    }
}