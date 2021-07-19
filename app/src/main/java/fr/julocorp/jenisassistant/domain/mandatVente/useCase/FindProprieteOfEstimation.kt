package fr.julocorp.jenisassistant.domain.mandatVente.useCase

import fr.julocorp.jenisassistant.domain.common.ActionState
import fr.julocorp.jenisassistant.domain.common.Failure
import fr.julocorp.jenisassistant.domain.common.Loading
import fr.julocorp.jenisassistant.domain.common.Success
import fr.julocorp.jenisassistant.domain.mandatVente.Propriete
import fr.julocorp.jenisassistant.domain.mandatVente.exception.ProprieteNotFound
import fr.julocorp.jenisassistant.domain.mandatVente.repository.ProprieteRepository
import java.util.*
import javax.inject.Inject

class FindProprieteOfEstimation @Inject constructor(
    private val proprieteRepository: ProprieteRepository
) {
    suspend fun handle(proprieteId: UUID): ActionState<Propriete> =
        try {
            val propriete = proprieteRepository.findPropriete(proprieteId)
            if (propriete == null) {
                Failure(ProprieteNotFound())
            } else {
                Success(propriete)
            }
        } catch (e: Throwable) {
            Failure(e)
        }

}