package fr.julocorp.jenisassistant.infrastructure.calendar.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
abstract class CalendarEstimationWithProprietaireDao {
    @Insert
    abstract fun insertCalendarEstimation(calendarEstimation: CalendarEstimationEntity)

    @Insert
    abstract fun insertProprietaire(proprietaire: ProprietaireEntity)

    @Transaction
    open fun insertCalendarEstimationWithProprietaire(
        calendarEstimation: CalendarEstimationEntity,
        proprietaire: ProprietaireEntity
    ) {
        insertCalendarEstimation(calendarEstimation)
        insertProprietaire(proprietaire)
    }

    @Transaction
    @Query("SELECT * FROM ${CalendarEstimationEntity.CALENDAR_ESTIMATION_TABLE} " +
            "WHERE `closed_at` IS NULL ORDER BY `estimation_date`")
    abstract fun getOnGoingRendezvousEstimation(): List<CalendarEstimationWithProprietaire>
}