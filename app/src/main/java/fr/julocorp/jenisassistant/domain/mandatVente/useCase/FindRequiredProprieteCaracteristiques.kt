package fr.julocorp.jenisassistant.domain.mandatVente.useCase

import fr.julocorp.jenisassistant.domain.common.ActionState
import fr.julocorp.jenisassistant.domain.common.Failure
import fr.julocorp.jenisassistant.domain.common.Success
import fr.julocorp.jenisassistant.domain.mandatVente.caracteristique.Caracteristique
import fr.julocorp.jenisassistant.domain.mandatVente.repository.CaracteristiqueRepository
import java.util.*
import javax.inject.Inject

class FindRequiredProprieteCaracteristiques @Inject constructor(
    private val caracteristiqueRepository: CaracteristiqueRepository
) {
    suspend fun handle(proprieteId: UUID): ActionState<List<Caracteristique<*>>> =
        try {
            val caracteristiques = caracteristiqueRepository.findRequiredProprieteCaracteristiques(proprieteId)
            Success(caracteristiques)
        } catch (e: Throwable) {
            Failure(e)
        }
}
