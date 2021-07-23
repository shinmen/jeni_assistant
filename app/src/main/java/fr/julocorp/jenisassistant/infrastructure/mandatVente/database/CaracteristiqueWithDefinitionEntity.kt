package fr.julocorp.jenisassistant.infrastructure.mandatVente.database

import androidx.room.Embedded
import androidx.room.Relation

data class CaracteristiqueWithDefinitionEntity(
    @Embedded val caracteristique: CaracteristiqueEntity,
    @Relation(
        entity = CaracteristiqueDefinitionEntity::class,
        parentColumn = "id",
        entityColumn = "definition_id"
    )
    val definition: CaracteristiqueDefinitionEntity,
)