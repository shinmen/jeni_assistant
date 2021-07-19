package fr.julocorp.jenisassistant.domain.mandatVente

import fr.julocorp.jenisassistant.domain.mandatVente.caracteristique.Caracteristique

data class Amenagement(
    val label: String,
    val amenagementType: AmenagementType,
    val caracteristiques: List<Caracteristique<*>>
)
