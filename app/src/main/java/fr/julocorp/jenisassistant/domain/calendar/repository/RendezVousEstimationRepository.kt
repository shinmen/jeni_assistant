package fr.julocorp.jenisassistant.domain.calendar.repository

import fr.julocorp.jenisassistant.domain.mandatVente.RendezVousEstimation
import java.util.*

interface RendezVousEstimationRepository {
    suspend fun scheduleRendezVousEstimation(rendezVousEstimation: RendezVousEstimation)

    suspend fun endRendezVousEstimation(rendezVousEstimationId: UUID)

    suspend fun findRendezVousEstimations(): List<RendezVousEstimation>
}