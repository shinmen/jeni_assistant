package fr.julocorp.jenisassistant.infrastructure.database

import androidx.room.Database
import androidx.room.RoomDatabase
import fr.julocorp.jenisassistant.infrastructure.common.database.RappelEntity
import fr.julocorp.jenisassistant.infrastructure.common.database.RappelDao

@Database(
    entities = [RappelEntity::class],
    version = JenisAssistantDatabase.DATABASE_VERSION
)
abstract class JenisAssistantDatabase : RoomDatabase() {
    abstract fun rappelDao(): RappelDao

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "jeni_assistant_database.db"
    }
}