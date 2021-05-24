package fr.julocorp.jenisassistant.infrastructure.common.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import fr.julocorp.jenisassistant.infrastructure.common.database.RappelEntity.Companion.RAPPEL_TABLE

@Dao
interface RappelDao {
    @Query("SELECT * FROM $RAPPEL_TABLE WHERE `closedAt` IS NOT NULL")
    fun getOnGoingRappels(): List<RappelEntity>

    @Insert
    fun scheduleRappel(rappelEntity: RappelEntity)

    @Query("UPDATE $RAPPEL_TABLE SET `closedAt` = :closedAt AND uid = :uid")
    fun closeRappel(uid: String, closedAt: Long)
}