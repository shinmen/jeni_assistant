package fr.julocorp.jenisassistant.domain.mandatVente.caracteristique

import fr.julocorp.jenisassistant.infrastructure.castGeneric
import java.util.*

data class CaracteristiqueBool(
    override val id: UUID,
    override val label: String,
    override val valeur: Boolean,
) : Caracteristique<Boolean> {
    override fun <U> valeurIn(listToSearchIn: List<U>): Boolean =
        castGeneric<List<Boolean>>(listToSearchIn).contains(valeur)

    override fun <U> valeurEquals(equalsTo: U): Boolean = valeur == equalsTo
}
