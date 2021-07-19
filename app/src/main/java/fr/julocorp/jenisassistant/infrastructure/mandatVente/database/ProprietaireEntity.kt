package fr.julocorp.jenisassistant.infrastructure.mandatVente.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = ProprietaireEntity.PROPRIETAIRE_TABLE)
data class ProprietaireEntity(
    @PrimaryKey val id: UUID,
    @ColumnInfo(name="fullname") val fullname: String,
    @ColumnInfo(name="phone_number") val phoneNumber: String?,
    @ColumnInfo(name="email") val email: String?,
    @ColumnInfo(name="commentaire") val commentaire: String?,
    ) {
    companion object {
        const val PROPRIETAIRE_TABLE = "proprietaires"
    }
}
