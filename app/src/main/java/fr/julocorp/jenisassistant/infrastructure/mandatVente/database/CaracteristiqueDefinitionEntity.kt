package fr.julocorp.jenisassistant.infrastructure.mandatVente.database

import androidx.room.*
import fr.julocorp.jenisassistant.domain.mandatVente.AmenagementType

@Entity(
    tableName = CaracteristiqueDefinitionEntity.CARACTERISTIQUE_DEFINITION_TABLE,
    indices = [
        Index(value = ["amenagement"]),
    ]
)
@TypeConverters(AmenagementTypeConverter::class, CaracteristiqueValueTypeConverter::class)
data class CaracteristiqueDefinitionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val label: String,
    @ColumnInfo(name = "validation_rules", defaultValue = "[]")
    val validationRules: List<String> = listOf(),
    @ColumnInfo(name = "available_values", defaultValue = "[]")
    val availableValues: List<String> = listOf(),
    val suffix: String?,
    @ColumnInfo(name="amenagement")
    val amenagement: AmenagementType,
    @ColumnInfo(name="type")
    val type: CaracteristiqueValueType
    ) {
    companion object {
        const val CARACTERISTIQUE_DEFINITION_TABLE = "caracteristiques_definitions"
    }
}
