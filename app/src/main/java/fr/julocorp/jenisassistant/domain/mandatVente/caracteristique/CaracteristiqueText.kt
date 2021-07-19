package fr.julocorp.jenisassistant.domain.mandatVente.caracteristique

import java.util.*

data class CaracteristiqueText(
    val id: UUID,
    val label: String,
    override val valeur: String
) : Caracteristique<String>
