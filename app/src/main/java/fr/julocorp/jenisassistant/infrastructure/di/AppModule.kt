package fr.julocorp.jenisassistant.infrastructure.di

import dagger.Module
import dagger.Provides
import fr.julocorp.jenisassistant.infrastructure.common.CoroutineContextProvider

@Module
class AppModule {
    @Provides
    fun provideCoroutineContext() = CoroutineContextProvider()
}