package fr.julocorp.jenisassistant.infrastructure.calendar.database

import androidx.room.*
import fr.julocorp.jenisassistant.domain.common.FullAddress
import java.util.*

@Entity(tableName = CalendarEstimationEntity.CALENDAR_ESTIMATION_TABLE,
    indices = [
        Index(value = ["estimation_date"]),
        Index(value = ["proprietaire_id"])
    ]
)
@TypeConverters(AddressConverter::class)
data class CalendarEstimationEntity(
    @PrimaryKey val id: UUID,
    @ColumnInfo(name = "estimation_date") val rendezVousDate: Long,
    @ColumnInfo(name = "proprietaire_id") val proprietaireId: UUID,
    @ColumnInfo(name = "addresse") val addresse: FullAddress,
    @ColumnInfo(name = "closed_at") val closedAt: Long? = null,
) {
    companion object {
        const val CALENDAR_ESTIMATION_TABLE = "calendar_estimations"
    }
}