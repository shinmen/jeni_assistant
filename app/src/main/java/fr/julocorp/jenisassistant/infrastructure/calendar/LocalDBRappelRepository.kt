package fr.julocorp.jenisassistant.infrastructure.calendar

import fr.julocorp.jenisassistant.domain.common.Rappel
import fr.julocorp.jenisassistant.domain.common.repository.RappelRepository
import fr.julocorp.jenisassistant.infrastructure.common.database.RappelDao
import fr.julocorp.jenisassistant.infrastructure.common.database.RappelEntity
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.util.*
import javax.inject.Inject

class LocalDBRappelRepository @Inject constructor(private val rappelDao: RappelDao) :
    RappelRepository {
    override suspend fun scheduleRappel(rappel: Rappel) {
        rappelDao.scheduleRappel(
            RappelEntity(
                rappel.id.toString(),
                rappel.rappelDate.atZone(ZoneId.of("Europe/Paris")).toEpochSecond(),
                rappel.sujet
            )
        )
    }

    override suspend fun endRappel(rappelId: UUID) {
        rappelDao.closeRappel(
            rappelId.toString(),
            LocalDateTime.now().atZone(ZoneId.of("Europe/Paris")).toEpochSecond()
        )
    }

    override suspend fun findRappels(): List<Rappel> =
        rappelDao.getOnGoingRappels().map { rappelEntity ->
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