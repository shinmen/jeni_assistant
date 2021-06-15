package fr.julocorp.jenisassistant.infrastructure.database

import androidx.room.TypeConverter
import java.util.*

class UuidConverter {
    @TypeConverter
    fun fromUuid(uuid: UUID): String = uuid.toString()
    @TypeConverter
    fun toUuid(uuid: String): UUID = UUID.fromString(uuid)
}