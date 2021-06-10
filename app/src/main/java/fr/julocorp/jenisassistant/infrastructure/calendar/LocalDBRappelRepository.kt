package fr.julocorp.jenisassistant.infrastructure.calendar

import fr.julocorp.jenisassistant.domain.common.Rappel
import fr.julocorp.jenisassistant.domain.common.repository.RappelRepository
import fr.julocorp.jenisassistant.infrastructure.common.database.RoomRappelDao
import fr.julocorp.jenisassistant.infrastructure.common.database.RoomRappelEntity
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import javax.inject.Inject

class LocalDBRappelRepository @Inject constructor(private val roomRappelDao: RoomRappelDao) :
    RappelRepository {
    override suspend fun scheduleRappel(rappel: Rappel) {
        roomRappelDao.scheduleRappel(
            RoomRappelEntity(
                rappel.id.toString(),
                rappel.rappelDate.atZone(ZoneId.of("Europe/Paris")).toEpochSecond(),
                rappel.sujet
            )
        )
    }

    override suspend fun endRappel(rappelId: UUID) {
        roomRappelDao.closeRappel(
            rappelId.toString(),
            LocalDateTime.now().atZone(ZoneId.of("Europe/Paris")).toEpochSecond()
        )
    }

    override suspend fun findRappels(): List<Rappel> =
        roomRappelDao.getOnGoingRappels().map { rappelEntity ->
            val rappelDate = LocalDateTime.ofInstant(
                Instant.ofEpochSecond(rappelEntity.rappelDate),
                ZoneId.of("Europe/Paris")
            )
            Rappel(
                UUID.fromString(rappelEntity.uid),
                rappelDate,
                rappelEntity.sujet
            )
        }
}