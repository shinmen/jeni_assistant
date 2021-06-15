package fr.julocorp.jenisassistant.infrastructure.calendar.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import fr.julocorp.jenisassistant.infrastructure.calendar.database.RappelEntity.Companion.RAPPEL_TABLE
import java.util.*

@Dao
interface RappelDao {
    @Query("SELECT * FROM $RAPPEL_TABLE WHERE `closed_at` IS NULL ORDER BY `rappel_date`")
    fun getOnGoingRappels(): List<RappelEntity>

    @Insert
    fun scheduleRappel(rappelEntity: RappelEntity)

    @Query("UPDATE $RAPPEL_TABLE SET `closed_at` = :closedAt WHERE id = :uid")
    fun closeRappel(uid: UUID, closedAt: Long)
}