package fr.julocorp.jenisassistant.infrastructure.mandatVente.repository

import fr.julocorp.jenisassistant.domain.mandatVente.Amenagement
import fr.julocorp.jenisassistant.domain.mandatVente.Propriete
import fr.julocorp.jenisassistant.domain.mandatVente.caracteristique.Caracteristique
import fr.julocorp.jenisassistant.domain.mandatVente.repository.ProprieteRepository
import fr.julocorp.jenisassistant.infrastructure.CoroutineContextProvider
import fr.julocorp.jenisassistant.infrastructure.mandatVente.database.ProprieteDao
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

class LocalDBProprieteRepository @Inject constructor(
    private val proprieteDao: ProprieteDao,
    private val coroutineContextProvider: CoroutineContextProvider,
    private val caracteristiqueMapper: CaracteristiqueMapper,
) : ProprieteRepository {
    override suspend fun findPropriete(proprieteId: UUID): Propriete? =
        withContext(coroutineContextProvider.iO) {
            proprieteDao.findPropriete(proprieteId)?.run {
                Propriete(
                    proprieteEntity.id,
                    proprieteEntity.addresse,
                    caracteristiqueMapper.mapToPropriete(caracteristiques),
                    caracteristiqueMapper.mapToAmenagement(caracteristiques)
                )
            }
        }
}