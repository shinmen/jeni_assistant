package fr.julocorp.jenisassistant.domain.prospection

import fr.julocorp.jenisassistant.domain.common.Geolocation

data class AdresseProspect(
    val adresse: String,
    val geolocation: Geolocation,
    val commentaireAdresse: String?
)
