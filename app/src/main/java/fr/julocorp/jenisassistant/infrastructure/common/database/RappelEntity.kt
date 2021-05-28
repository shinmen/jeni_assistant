package fr.julocorp.jenisassistant.infrastructure.common.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import fr.julocorp.jenisassistant.infrastructure.common.database.RappelEntity.Companion.RAPPEL_TABLE

@Entity(tableName = RAPPEL_TABLE)
data class RappelEntity(
    @PrimaryKey val uid: String,
    @ColumnInfo(name = "rappel_date") val rappelDate: Long,
    @ColumnInfo(name = "sujet") val sujet: String,
    @ColumnInfo(name = "closed_at") val closedAt: Long? = null,
)  {
   companion object {
       const val RAPPEL_TABLE = "rappels"
   }
}