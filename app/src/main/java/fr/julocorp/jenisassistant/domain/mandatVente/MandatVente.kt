package fr.julocorp.jenisassistant.domain.mandatVente

import fr.julocorp.jenisassistant.domain.common.BusinessCalendar
import fr.julocorp.jenisassistant.domain.common.Rappel
import java.util.*

class MandatVente(
    val id: UUID,
    private val proprietaire: Proprietaire,
    private val bien: Bien
) {
    fun requestEstimation(calendar: BusinessCalendar, disponibilites: Calendar?) {
        if (disponibilites == null) {
            val scheduleCallTime = scheduleCallDisponibilites()
            calendar.addRappel(Rappel(UUID.randomUUID(), scheduleCallTime, bien.toString()))
        } else {
            calendar.addRendezVousEstimation(scheduleRendezVousEstimation(disponibilites))
        }
    }

    fun scheduleRendezVousEstimation(disponibilites: Calendar) = RendezVousEstimation(
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

    private fun scheduleCallDisponibilites(): Calendar
    {
        val tonightAtSeven = Calendar.getInstance().apply { set(Calendar.HOUR_OF_DAY, 19) }

        return if (Calendar.getInstance().after(tonightAtSeven)) {
            Calendar.getInstance().apply {
                add(Calendar.DATE, 1)
                set(Calendar.HOUR_OF_DAY, 9)
            }
        } else {
            tonightAtSeven
        }
    }
}