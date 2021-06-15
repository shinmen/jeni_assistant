package fr.julocorp.jenisassistant.domain.mandatVente

import fr.julocorp.jenisassistant.domain.common.FullAddress
import fr.julocorp.jenisassistant.domain.prospection.Contact
import java.time.LocalDateTime
import java.util.*

data class RendezVousEstimation(
    val id: UUID,
    val rendezVousDate: LocalDateTime,
    val addresseBien: FullAddress,
    val prospect: Contact
)