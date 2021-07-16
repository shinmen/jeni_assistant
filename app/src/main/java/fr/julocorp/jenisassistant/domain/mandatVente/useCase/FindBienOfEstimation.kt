package fr.julocorp.jenisassistant.domain.mandatVente.useCase

import fr.julocorp.jenisassistant.domain.common.ActionState
import fr.julocorp.jenisassistant.domain.common.Loading
import fr.julocorp.jenisassistant.domain.mandatVente.Caracteristique
import fr.julocorp.jenisassistant.domain.mandatVente.Propriete
import java.util.*
import javax.inject.Inject

class FindBienOfEstimation @Inject constructor() {
    fun handle(): ActionState<Propriete> {
        val a = Caracteristique(UUID.randomUUID(), "surface", "youhou")
        val t = Caracteristique<List<String>>(UUID.randomUUID(), "surface", listOf())

        return Loading()
    }
}