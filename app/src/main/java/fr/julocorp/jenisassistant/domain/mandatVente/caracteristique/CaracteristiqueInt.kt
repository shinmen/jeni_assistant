package fr.julocorp.jenisassistant.domain.mandatVente.caracteristique

import java.util.*

data class CaracteristiqueInt(
    val id: UUID,
    val label: String,
    override val valeur: Int
) : Caracteristique<Int>
