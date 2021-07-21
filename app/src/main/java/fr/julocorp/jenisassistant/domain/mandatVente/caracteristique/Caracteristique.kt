package fr.julocorp.jenisassistant.domain.mandatVente.caracteristique

import java.util.*

interface Caracteristique<T> {
    val valeur: T
    val label: String
    val id: UUID

    fun <T>valeurIn(listToSearchIn: List<T>): Boolean

    fun <T>valeurEquals(equalsTo: T): Boolean
}