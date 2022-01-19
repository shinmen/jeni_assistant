package fr.julocorp.jenisassistant.domain.mandatVente.caracteristique

import fr.julocorp.jenisassistant.infrastructure.castGeneric
import java.util.*

data class CaracteristiqueMultiple(
    override val id: UUID,
    override val label: String,
    override val valeur: List<String>,
    override val definition: Definition
) : Caracteristique<List<String>> {
    override fun <U> valeurIn(listToSearchIn: List<U>): Boolean =
        castGeneric<List<String>>(listToSearchIn).containsAll(valeur)

    override fun <U> valeurEquals(equalsTo: U): Boolean = valeur == equalsTo
}
