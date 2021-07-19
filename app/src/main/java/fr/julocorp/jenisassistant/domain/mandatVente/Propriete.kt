package fr.julocorp.jenisassistant.domain.mandatVente

import fr.julocorp.jenisassistant.domain.common.FullAddress
import fr.julocorp.jenisassistant.domain.mandatVente.caracteristique.Caracteristique
import java.util.*

data class Propriete(
    val id: UUID,
    val adresse: FullAddress,
    val caracteristiques: List<Caracteristique<*>>,
    val amenagements: List<Amenagement>
)
