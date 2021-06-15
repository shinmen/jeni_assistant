package fr.julocorp.jenisassistant.infrastructure.calendar.database

import androidx.room.Dao
import androidx.room.Query
import java.util.*

@Dao
interface CalendarEstimationDao {
    @Query("UPDATE ${CalendarEstimationEntity.CALENDAR_ESTIMATION_TABLE} SET `closed_at` = :closedAt WHERE id = :uid" )
    fun cancelRendezVousEstimation(uid: UUID, closedAt: Long)
}