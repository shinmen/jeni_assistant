package fr.julocorp.jenisassistant.domain.mandatVente.repository

import fr.julocorp.jenisassistant.domain.mandatVente.caracteristique.Caracteristique
import java.util.*

interface CaracteristiqueRepository {
    suspend fun findRequiredProprieteCaracteristiques(proprieteId: UUID): List<Caracteristique<*>>
}