package fr.julocorp.jenisassistant.domain.mandatVente

import fr.julocorp.jenisassistant.domain.common.FullAddress
import fr.julocorp.jenisassistant.domain.mandatVente.caracteristique.Caracteristique
import java.util.*

data class Propriete(
    val id: UUID,
    val adresse: FullAddress,
    val caracteristiques: List<Caracteristique<*>>,
    val amenagements: List<Amenagement>
) {
    companion object {
        const val CARACTERISTIQUE_PROPRIETE_NATURE_LABEL = "nature de la propriété"
        const val CARACTERISTIQUE_PROPRIETE_SURFACE_INTERIEURE_LABEL = "surface intérieure"
        const val CARACTERISTIQUE_PROPRIETE_SURFACE_EXTERIEURE_LABEL = "surface extérieure"
    }
}
