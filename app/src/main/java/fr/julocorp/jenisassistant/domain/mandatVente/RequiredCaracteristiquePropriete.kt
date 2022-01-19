package fr.julocorp.jenisassistant.domain.mandatVente

import fr.julocorp.jenisassistant.domain.mandatVente.Propriete.Companion.CARACTERISTIQUE_PROPRIETE_NATURE_LABEL
import fr.julocorp.jenisassistant.domain.mandatVente.Propriete.Companion.CARACTERISTIQUE_PROPRIETE_SURFACE_INTERIEURE_LABEL

enum class RequiredCaracteristiquePropriete(val label: String) {
    PROPRIETE_NATURE(CARACTERISTIQUE_PROPRIETE_NATURE_LABEL),
    SURFACE_HABITABLE(CARACTERISTIQUE_PROPRIETE_SURFACE_INTERIEURE_LABEL),
}