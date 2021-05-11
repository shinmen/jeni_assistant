package fr.julocorp.jenisassistant.infrastructure.di

import dagger.Module
import dagger.Provides
import fr.julocorp.jenisassistant.ui.common.Dummy

@Module
class TestModule {
    @Provides
    fun provideDummy(): Dummy = Dummy()
}