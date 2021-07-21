package fr.julocorp.jenisassistant.domain.mandatVente.caracteristique

import fr.julocorp.jenisassistant.infrastructure.castGeneric
import java.util.*

data class CaracteristiqueText(
    override val id: UUID,
    override val label: String,
    override val valeur: String
) : Caracteristique<String> {
    override fun <U> valeurIn(listToSearchIn: List<U>): Boolean =
        castGeneric<List<String>>(listToSearchIn).contains(valeur)

    override fun <U> valeurEquals(equalsTo: U): Boolean = valeur == equalsTo
}
