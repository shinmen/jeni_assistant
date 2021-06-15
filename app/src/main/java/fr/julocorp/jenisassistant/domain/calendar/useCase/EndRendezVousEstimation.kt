package fr.julocorp.jenisassistant.domain.calendar.useCase

import fr.julocorp.jenisassistant.domain.calendar.repository.RendezVousEstimationRepository
import fr.julocorp.jenisassistant.domain.common.ActionState
import fr.julocorp.jenisassistant.domain.common.Failure
import fr.julocorp.jenisassistant.domain.common.Success
import fr.julocorp.jenisassistant.infrastructure.CoroutineContextProvider
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

class EndRendezVousEstimation @Inject constructor(
    private val rendezVousEstimationRepository: RendezVousEstimationRepository,
    private val coroutineContextProvider: CoroutineContextProvider
) {
    suspend fun handle(rendezVousEstimationId: UUID): ActionState<Boolean> = withContext(coroutineContextProvider.iO) {
        try {
            rendezVousEstimationRepository.endRendezVousEstimation(rendezVousEstimationId)
            Success(true)
        } catch (e: Throwable) {
            Failure(e)
        }
    }
}
