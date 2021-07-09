package fr.julocorp.jenisassistant.domain.calendar.useCase

import fr.julocorp.jenisassistant.domain.calendar.repository.RendezVousEstimationRepository
import fr.julocorp.jenisassistant.domain.common.ActionState
import fr.julocorp.jenisassistant.domain.common.Failure
import fr.julocorp.jenisassistant.domain.common.FullAddress
import fr.julocorp.jenisassistant.domain.common.Success
import fr.julocorp.jenisassistant.domain.mandatVente.RendezVousEstimation
import javax.inject.Inject

class ScheduleRendezVousEstimation @Inject constructor(
    private val rendezVousEstimationRepository: RendezVousEstimationRepository,
) {
    suspend fun handle(rendezVousEstimation: RendezVousEstimation): ActionState<Boolean> =
        try {
            rendezVousEstimationRepository.scheduleRendezVousEstimation(rendezVousEstimation)
            Success(true)
        } catch (e: Throwable) {
            Failure(e)
        }
}