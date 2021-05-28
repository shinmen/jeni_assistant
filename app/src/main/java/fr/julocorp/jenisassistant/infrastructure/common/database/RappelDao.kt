package fr.julocorp.jenisassistant.infrastructure.common.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import fr.julocorp.jenisassistant.infrastructure.common.database.RappelEntity.Companion.RAPPEL_TABLE

@Dao
interface RappelDao {
    @Query("SELECT * FROM $RAPPEL_TABLE WHERE `closed_at` IS NULL ORDER BY `rappel_date`")
    fun getOnGoingRappels(): List<RappelEntity>

    @Insert
    fun scheduleRappel(rappelEntity: RappelEntity)

    @Query("UPDATE $RAPPEL_TABLE SET `closed_at` = :closedAt WHERE uid = :uid")
    fun closeRappel(uid: String, closedAt: Long)
}