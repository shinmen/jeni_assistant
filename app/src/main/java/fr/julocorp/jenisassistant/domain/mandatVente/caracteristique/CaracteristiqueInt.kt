package fr.julocorp.jenisassistant.domain.mandatVente.caracteristique

import fr.julocorp.jenisassistant.infrastructure.castGeneric
import java.util.*

data class CaracteristiqueInt(
    override val id: UUID,
    override val label: String,
    override val valeur: Int,
    override val definition: Definition
) : Caracteristique<Int> {
    override fun <U> valeurIn(listToSearchIn: List<U>): Boolean =
        castGeneric<List<Int>>(listToSearchIn).contains(valeur)

    override fun <U> valeurEquals(equalsTo: U): Boolean = valeur == equalsTo
}
