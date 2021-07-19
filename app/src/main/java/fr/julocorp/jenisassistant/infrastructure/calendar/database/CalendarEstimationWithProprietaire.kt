package fr.julocorp.jenisassistant.infrastructure.calendar.database

import androidx.room.Embedded
import androidx.room.Relation
import fr.julocorp.jenisassistant.infrastructure.mandatVente.database.ProprietaireEntity

data class CalendarEstimationWithProprietaire(
    @Embedded val calendarEstimation: CalendarEstimationEntity,

    @Relation(
        entity = ProprietaireEntity::class,
        parentColumn = "proprietaire_id",
        entityColumn = "id"
    )
    val proprietaire: ProprietaireEntity,
)
