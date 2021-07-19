package fr.julocorp.jenisassistant.infrastructure.di

import dagger.Binds
import dagger.Module
import fr.julocorp.jenisassistant.domain.calendar.repository.RappelRepository
import fr.julocorp.jenisassistant.domain.calendar.repository.RendezVousEstimationRepository
import fr.julocorp.jenisassistant.domain.common.repository.AdresseSearcher
import fr.julocorp.jenisassistant.domain.mandatVente.repository.ProprieteRepository
import fr.julocorp.jenisassistant.infrastructure.calendar.repository.LocalDBRappelRepository
import fr.julocorp.jenisassistant.infrastructure.calendar.repository.LocalDBRendezVousEstimationRepository
import fr.julocorp.jenisassistant.infrastructure.common.repository.apiGeoGouv.ApiGeoGouvAdresseSearcher
import fr.julocorp.jenisassistant.infrastructure.mandatVente.repository.LocalDBProprieteRepository

@Module
abstract class RepositoryModule {
    @Binds
    abstract fun bindRappelRepository(rappelRepository: LocalDBRappelRepository): RappelRepository

    @Binds
    abstract fun bindGeoGouvRepository(adresseSearcher: ApiGeoGouvAdresseSearcher): AdresseSearcher

    @Binds
    abstract fun bindRDVEstimationRepository(rendezVousEstimationRepository: LocalDBRendezVousEstimationRepository): RendezVousEstimationRepository

    @Binds
    abstract fun bindProprieteRepository(proprieteRepository: LocalDBProprieteRepository): ProprieteRepository
}