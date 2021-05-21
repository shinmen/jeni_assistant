package fr.julocorp.jenisassistant.infrastructure.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import fr.julocorp.jenisassistant.domain.common.repository.RappelRepository
import fr.julocorp.jenisassistant.infrastructure.common.LocalDBRappelRepository


@Module
abstract class LocalDbModule {
    @Binds
    abstract fun bindRappelRepository(rappelRepository: LocalDBRappelRepository): RappelRepository
}