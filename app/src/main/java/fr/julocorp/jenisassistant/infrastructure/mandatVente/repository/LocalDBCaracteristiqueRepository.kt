package fr.julocorp.jenisassistant.infrastructure.mandatVente.repository

import fr.julocorp.jenisassistant.domain.mandatVente.RequiredCaracteristiquePropriete
import fr.julocorp.jenisassistant.domain.mandatVente.caracteristique.Caracteristique
import fr.julocorp.jenisassistant.domain.mandatVente.repository.CaracteristiqueRepository
import fr.julocorp.jenisassistant.infrastructure.CoroutineContextProvider
import fr.julocorp.jenisassistant.infrastructure.mandatVente.database.CaracteristiqueDao
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

class LocalDBCaracteristiqueRepository @Inject constructor(
    private val caracteristiqueDao: CaracteristiqueDao,
    private val coroutineContextProvider: CoroutineContextProvider,
    private val mapper: CaracteristiqueMapper
) : CaracteristiqueRepository {
    override suspend fun findRequiredProprieteCaracteristiques(proprieteId: UUID): List<Caracteristique<*>> =
        withContext(coroutineContextProvider.iO) {
            mapper.mapToPropriete(
                caracteristiqueDao.findCaracteristiquesProprieteByLabel(
                    proprieteId,
                    listOf(
                        RequiredCaracteristiquePropriete.PROPRIETE_NATURE.label,
                        RequiredCaracteristiquePropriete.SURFACE_HABITABLE.label
                    )
                )
            )
        }
}