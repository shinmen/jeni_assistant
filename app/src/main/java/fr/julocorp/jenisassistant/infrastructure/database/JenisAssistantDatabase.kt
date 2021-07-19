package fr.julocorp.jenisassistant.infrastructure.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import fr.julocorp.jenisassistant.infrastructure.calendar.database.*
import fr.julocorp.jenisassistant.infrastructure.mandatVente.database.*

@Database(
    entities = [
        RappelEntity::class,
        CalendarEstimationEntity::class,
        ProprietaireEntity::class,
        ProprieteEntity::class,
        CaracteristiqueEntity::class,
        ProprieteProprietaireCrossRef::class,
    ],
    version = JenisAssistantDatabase.DATABASE_VERSION
)
@TypeConverters(UuidConverter::class)
abstract class JenisAssistantDatabase : RoomDatabase() {
    abstract fun rappelDao(): RappelDao

    abstract fun calendarEstimationDao(): CalendarEstimationDao

    abstract fun calendarEstimationWithProprietaireDao(): CalendarEstimationWithProprietaireDao

    abstract fun proprieteDao(): ProprieteDao

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "jeni_assistant_database.db"
    }
}