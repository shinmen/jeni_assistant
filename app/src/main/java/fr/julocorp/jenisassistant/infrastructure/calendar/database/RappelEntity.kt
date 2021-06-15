package fr.julocorp.jenisassistant.infrastructure.calendar.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import fr.julocorp.jenisassistant.infrastructure.calendar.database.RappelEntity.Companion.RAPPEL_TABLE
import java.util.*

@Entity(
    tableName = RAPPEL_TABLE,
    indices = [
        Index(value = ["rappel_date"]),
    ]
)
data class RappelEntity(
    @PrimaryKey val id: UUID,
    @ColumnInfo(name = "rappel_date") val rappelDate: Long,
    @ColumnInfo(name = "sujet") val sujet: String,
    @ColumnInfo(name = "closed_at") val closedAt: Long? = null,
) {
    companion object {
        const val RAPPEL_TABLE = "rappels"
    }
}