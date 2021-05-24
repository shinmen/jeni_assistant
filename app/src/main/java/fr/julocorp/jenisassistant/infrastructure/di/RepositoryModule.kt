package fr.julocorp.jenisassistant.infrastructure.di

import dagger.Binds
import dagger.Module
import fr.julocorp.jenisassistant.domain.common.repository.RappelRepository
import fr.julocorp.jenisassistant.infrastructure.calendar.LocalDBRappelRepository

@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindRappelRepository(rappelRepository: LocalDBRappelRepository): RappelRepository
}