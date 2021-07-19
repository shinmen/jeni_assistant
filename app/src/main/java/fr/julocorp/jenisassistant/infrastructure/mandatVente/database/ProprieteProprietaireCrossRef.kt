package fr.julocorp.jenisassistant.infrastructure.mandatVente.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import fr.julocorp.jenisassistant.infrastructure.calendar.database.RappelEntity
import java.util.*

@Entity(
    tableName = ProprieteProprietaireCrossRef.PROPRIETE_PROPRIETAIRE_TABLE,
    primaryKeys = ["propriete_id", "proprietaire_id"]
)
data class ProprieteProprietaireCrossRef(
    @ColumnInfo(name = "propriete_id") val proprieteId: UUID,
    @ColumnInfo(name = "proprietaire_id") val proprietaireId: UUID
) {
    companion object {
        const val PROPRIETE_PROPRIETAIRE_TABLE = "proprietes_proprietaires"
    }
}
