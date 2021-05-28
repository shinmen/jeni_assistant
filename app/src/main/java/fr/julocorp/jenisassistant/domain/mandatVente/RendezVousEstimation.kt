package fr.julocorp.jenisassistant.domain.mandatVente

import java.time.LocalDateTime

data class RendezVousEstimation(
    val rendezVousDate: LocalDateTime,
    val addresseBien: AdresseBien,
    val telephone: String,
    val mandat: MandatVente
)