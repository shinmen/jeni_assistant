package fr.julocorp.jenisassistant.infrastructure.di

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import fr.julocorp.jenisassistant.infrastructure.CoroutineContextProvider
import fr.julocorp.jenisassistant.infrastructure.mandatVente.database.CaracteristiqueValue
import fr.julocorp.jenisassistant.infrastructure.mandatVente.repository.CaracteristiqueMapper

@Module
class AppModule {
    @Provides
    fun provideCoroutineContext() = CoroutineContextProvider()

    @Provides
    fun provideCaracteristiqueMapper() = CaracteristiqueMapper()
}