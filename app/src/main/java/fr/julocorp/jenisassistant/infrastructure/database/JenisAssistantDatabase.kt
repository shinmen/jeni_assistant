package fr.julocorp.jenisassistant.infrastructure.database

import androidx.room.Database
import androidx.room.RoomDatabase
import fr.julocorp.jenisassistant.infrastructure.calendar.database.RoomRappelEntity
import fr.julocorp.jenisassistant.infrastructure.calendar.database.RoomRappelDao

@Database(
    entities = [RoomRappelEntity::class],
    version = JenisAssistantDatabase.DATABASE_VERSION
)
abstract class JenisAssistantDatabase : RoomDatabase() {
    abstract fun rappelDao(): RoomRappelDao

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "jeni_assistant_database.db"
    }
}