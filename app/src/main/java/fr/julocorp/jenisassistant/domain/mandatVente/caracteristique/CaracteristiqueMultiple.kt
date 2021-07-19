package fr.julocorp.jenisassistant.domain.mandatVente.caracteristique

import java.util.*

data class CaracteristiqueMultiple(
    val id: UUID,
    val label: String,
    override val valeur: List<String>
) : Caracteristique<List<String>>
