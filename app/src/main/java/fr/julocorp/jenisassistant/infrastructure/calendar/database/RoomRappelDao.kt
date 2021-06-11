package fr.julocorp.jenisassistant.infrastructure.calendar.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import fr.julocorp.jenisassistant.infrastructure.calendar.database.RoomRappelEntity.Companion.RAPPEL_TABLE

@Dao
interface RoomRappelDao {
    @Query("SELECT * FROM $RAPPEL_TABLE WHERE `closed_at` IS NULL ORDER BY `rappel_date`")
    fun getOnGoingRappels(): List<RoomRappelEntity>

    @Insert
    fun scheduleRappel(roomRappelEntity: RoomRappelEntity)

    @Query("UPDATE $RAPPEL_TABLE SET `closed_at` = :closedAt WHERE uid = :uid")
    fun closeRappel(uid: String, closedAt: Long)
}