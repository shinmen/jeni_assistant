package fr.julocorp.jenisassistant.infrastructure.mandatVente.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import java.util.*

@Dao
interface ProprieteDao {
    @Transaction
    @Query("SELECT * FROM ${ProprieteEntity.PROPRIETE_TABLE} WHERE id = :proprieteId")
    fun findPropriete(proprieteId: UUID): ProprieteWithCaracteristiquesEntity?
}