package fr.julocorp.jenisassistant.infrastructure.calendar.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import fr.julocorp.jenisassistant.infrastructure.mandatVente.database.ProprietaireEntity
import fr.julocorp.jenisassistant.infrastructure.mandatVente.database.ProprieteEntity
import fr.julocorp.jenisassistant.infrastructure.mandatVente.database.ProprieteProprietaireCrossRef
import java.util.*

@Dao
abstract class CalendarEstimationWithProprietaireDao {
    @Insert
    abstract fun insertCalendarEstimation(calendarEstimation: CalendarEstimationEntity)

    @Insert
    abstract fun insertProprietaire(proprietaire: ProprietaireEntity)

    @Insert
    abstract fun insertPropriete(propriete: ProprieteEntity)

    @Query("INSERT INTO ${ProprieteProprietaireCrossRef.PROPRIETE_PROPRIETAIRE_TABLE} " +
            "(propriete_id, proprietaire_id) VALUES (:proprieteId, :proprietaireId)")
    abstract fun insertLinkProprieteProprietaire(proprieteId: UUID, proprietaireId: UUID)

    @Transaction
    open fun insertCalendarEstimationWithProprietaire(
        calendarEstimation: CalendarEstimationEntity,
        proprietaire: ProprietaireEntity,
        propriete: ProprieteEntity
    ) {
        insertCalendarEstimation(calendarEstimation)
        insertProprietaire(proprietaire)
        insertPropriete(propriete)
        insertLinkProprieteProprietaire(propriete.id, proprietaire.id)
    }

    @Transaction
    @Query("SELECT * FROM ${CalendarEstimationEntity.CALENDAR_ESTIMATION_TABLE} " +
            "WHERE `closed_at` IS NULL ORDER BY `estimation_date`")
    abstract fun getOnGoingRendezvousEstimation(): List<CalendarEstimationWithProprietaire>

    @Transaction
    @Query("SELECT * FROM ${CalendarEstimationEntity.CALENDAR_ESTIMATION_TABLE} " +
            "WHERE id = :id LIMIT 1")
    abstract fun findRendezvousEstimation(id: UUID): CalendarEstimationWithProprietaire?
}