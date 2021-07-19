package fr.julocorp.jenisassistant.infrastructure.mandatVente.database

import androidx.room.Embedded
import androidx.room.Relation

data class ProprieteWithCaracteristiquesEntity(
    @Embedded val proprieteEntity: ProprieteEntity,
    @Relation(
        entity = CaracteristiqueEntity::class,
        parentColumn = "id",
        entityColumn = "propriete_id"
    )
    val caracteristiques: List<CaracteristiqueEntity>,
)