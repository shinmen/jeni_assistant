package fr.julocorp.jenisassistant.infrastructure.calendar.database

import androidx.room.Embedded
import androidx.room.Relation

data class CalendarEstimationWithProprietaire(
    @Embedded val calendarEstimation: CalendarEstimationEntity,

    @Relation(
        entity = ProprietaireEntity::class,
        parentColumn = "proprietaire_id",
        entityColumn = "id"
    )
    val proprietaire: ProprietaireEntity,
)
