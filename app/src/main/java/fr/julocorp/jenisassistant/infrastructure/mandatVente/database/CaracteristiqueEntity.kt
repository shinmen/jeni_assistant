package fr.julocorp.jenisassistant.infrastructure.mandatVente.database

import androidx.room.*
import java.util.*

@Entity(tableName = CaracteristiqueEntity.CARACTERISTIQUE_TABLE,
    indices = [
        Index(value = ["propriete_id"]),
        Index(value = ["definition_id"]),
        Index(value = ["amenagement_label"]),
    ])
@TypeConverters(CaracteristiqueValueConverter::class)
data class CaracteristiqueEntity(
    @PrimaryKey val id: UUID,
    @ColumnInfo(name = "propriete_id") val proprieteId: UUID,
    @ColumnInfo(name = "definition_id") val definitionId: Long,
    val label: String,
    val valeur: CaracteristiqueValue,
    @Embedded val amenagement: AmenagementEmbed,
) {
    companion object {
        const val CARACTERISTIQUE_TABLE = "caracteristiques"
    }
}
