package fr.julocorp.jenisassistant.domain.mandatVente

import fr.julocorp.jenisassistant.domain.common.Geolocation

data class AdresseBien(
    val address: String,
    val geolocation: Geolocation
)
