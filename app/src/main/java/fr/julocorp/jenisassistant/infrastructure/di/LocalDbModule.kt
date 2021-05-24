package fr.julocorp.jenisassistant.infrastructure.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import fr.julocorp.jenisassistant.infrastructure.database.JenisAssistantDatabase
import javax.inject.Singleton


@Module
open class LocalDbModule {
    @Singleton
    @Provides
    open fun providesRoomDatabase(app: Application) = Room.databaseBuilder(
        app,
        JenisAssistantDatabase::class.java,
        JenisAssistantDatabase.DATABASE_NAME
    )
        .build()

    @Singleton
    @Provides
    open fun providesRappelDao(database: JenisAssistantDatabase) = database.rappelDao()
}