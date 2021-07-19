package fr.julocorp.jenisassistant.infrastructure.mandatVente.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import fr.julocorp.jenisassistant.domain.common.FullAddress
import fr.julocorp.jenisassistant.infrastructure.database.AddressConverter
import java.util.*

@Entity(tableName = ProprieteEntity.PROPRIETE_TABLE)
@TypeConverters(AddressConverter::class)
data class ProprieteEntity(
    @PrimaryKey val id: UUID,
    @ColumnInfo(name = "addresse") val addresse: FullAddress,
) {
    companion object {
        const val PROPRIETE_TABLE = "proprietes"
    }
}
