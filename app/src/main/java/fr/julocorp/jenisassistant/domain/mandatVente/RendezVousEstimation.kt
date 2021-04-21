package fr.julocorp.jenisassistant.domain.mandatVente

import java.util.*

data class RendezVousEstimation(
    val rendezVousDate: Calendar,
    val addresseBien: AdresseBien,
    val telephone: String,
    val mandat: MandatVente
)