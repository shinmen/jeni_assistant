package fr.julocorp.jenisassistant.domain.mandatVente.caracteristique

import java.util.*

data class CaracteristiqueFloat(
    val id: UUID,
    val label: String,
    override val valeur: Float
) : Caracteristique<Float>
