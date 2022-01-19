package fr.julocorp.jenisassistant.infrastructure.mandatVente.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import java.util.*

@Dao
abstract class CaracteristiqueDao {
    @Transaction
    open fun findCaracteristiquesProprieteByLabel(proprieteId: UUID, defaultLabels: List<String>): List<CaracteristiqueWithDefinitionEntity> {
        val definitionIds = findDefinitionsForLabels(defaultLabels)

        return findProprieteCaracteristiqueByDefinitions(definitionIds, proprieteId)
    }

    @Query("SELECT * FROM ${CaracteristiqueEntity.CARACTERISTIQUE_TABLE} WHERE definition_id IN (:definitionIds) AND propriete_id = :proprieteId")
    abstract fun findProprieteCaracteristiqueByDefinitions(definitionIds: List<Long>, proprieteId: UUID): List<CaracteristiqueWithDefinitionEntity>

    @Query("SELECT id FROM ${CaracteristiqueDefinitionEntity.CARACTERISTIQUE_DEFINITION_TABLE} WHERE label IN (:defaultLabels)")
    abstract fun findDefinitionsForLabels(defaultLabels: List<String>): List<Long>
}