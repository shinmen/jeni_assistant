package fr.julocorp.jenisassistant.domain.mandatVente.repository

import fr.julocorp.jenisassistant.domain.mandatVente.Propriete
import java.util.*

interface ProprieteRepository {
    suspend fun findPropriete(proprieteId: UUID): Propriete?
}