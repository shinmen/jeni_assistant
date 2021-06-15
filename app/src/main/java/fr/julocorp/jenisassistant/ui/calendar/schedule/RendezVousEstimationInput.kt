package fr.julocorp.jenisassistant.ui.calendar.schedule

import fr.julocorp.jenisassistant.domain.common.FullAddress
import fr.julocorp.jenisassistant.domain.prospection.Email
import fr.julocorp.jenisassistant.domain.prospection.PhoneNumber
import java.time.LocalDateTime

data class RendezVousEstimationInput(
    var address: FullAddress? = null,
    var rendezVousDate: LocalDateTime? = null,
    var fullName: String? = null,
    var phoneNumber: PhoneNumber? = null,
    var email: Email? = null,
    var comment: String? = null
)