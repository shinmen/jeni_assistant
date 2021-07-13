package fr.julocorp.jenisassistant.domain.mandatVente.useCase

import fr.julocorp.jenisassistant.domain.calendar.repository.RendezVousEstimationRepository
import fr.julocorp.jenisassistant.domain.common.ActionState
import fr.julocorp.jenisassistant.domain.common.Failure
import fr.julocorp.jenisassistant.domain.common.Success
import fr.julocorp.jenisassistant.domain.mandatVente.RendezVousEstimation
import fr.julocorp.jenisassistant.domain.mandatVente.exception.RendezVousNotFound
import java.util.*
import javax.inject.Inject

class FindRendezVousEstimationWithProprietaire @Inject constructor(
    private val rendezVousEstimationRepository: RendezVousEstimationRepository
) {
    suspend fun handle(rendezVousEstimationId: UUID): ActionState<RendezVousEstimation> =
        try {
            val rendezVousEstimation =
                rendezVousEstimationRepository.findRendezVousEstimation(rendezVousEstimationId)
            if (rendezVousEstimation == null) {
                Failure(RendezVousNotFound())
            } else {
                Success(rendezVousEstimation)
            }
        } catch (e: Throwable) {
            Failure(e)
        }
}