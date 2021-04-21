package fr.julocorp.jenisassistant.domain.common

import fr.julocorp.jenisassistant.domain.mandatVente.RendezVousEstimation

interface BusinessCalendar {
    fun addRappel(rappel: Rappel)

    fun addRendezVousEstimation(rendezVousEstimation: RendezVousEstimation)

    fun cancelRendezVousEstimation(rendezVousEstimation: RendezVousEstimation)

    fun closeRappel(rappel: Rappel)
}