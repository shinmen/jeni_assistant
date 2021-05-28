package fr.julocorp.jenisassistant.domain.mandatVente

import fr.julocorp.jenisassistant.domain.common.BusinessCalendar
import fr.julocorp.jenisassistant.domain.common.Rappel
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*

class MandatVente(
    val id: UUID,
    private val proprietaire: Proprietaire,
    private val bien: Bien
) {
    fun requestEstimation(calendar: BusinessCalendar, disponibilites: LocalDateTime?) {
        if (disponibilites == null) {
            val scheduleCallTime = scheduleCallDisponibilites()
            calendar.addRappel(Rappel(UUID.randomUUID(), scheduleCallTime, bien.toString()))
        } else {
            calendar.addRendezVousEstimation(scheduleRendezVousEstimation(disponibilites))
        }
    }

    fun scheduleRendezVousEstimation(disponibilites: LocalDateTime) = RendezVousEstimation(
        disponibilites,
        bien.adresse,
        proprietaire.telephones.first(),
        this
    )

    fun cancelRendezVousEstimation(calendar: BusinessCalendar, rendezVousEstimation: RendezVousEstimation) {
        calendar.cancelRendezVousEstimation(rendezVousEstimation)
    }

    fun cancelRappel(calendar: BusinessCalendar, rappel: Rappel) {
        calendar.closeRappel(rappel)
    }

    fun visiteBienToEstimate(): Estimation {
        return Estimation()
    }

    fun gagneMandat() {

    }

    private fun scheduleCallDisponibilites(): LocalDateTime
    {
        val now = LocalDateTime.now()
        val tonightAtSeven = LocalDateTime.now().withHour(19).withMinute(0)

        return if (now.isAfter(tonightAtSeven)) {
            now.withHour(9).withMinute(0)
        } else {
            tonightAtSeven
        }
    }
}