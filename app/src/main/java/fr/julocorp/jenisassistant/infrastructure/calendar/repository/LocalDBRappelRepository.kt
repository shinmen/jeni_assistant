package fr.julocorp.jenisassistant.infrastructure.calendar.repository

import fr.julocorp.jenisassistant.domain.calendar.repository.RappelRepository
import fr.julocorp.jenisassistant.domain.common.Rappel
import fr.julocorp.jenisassistant.infrastructure.calendar.database.RappelDao
import fr.julocorp.jenisassistant.infrastructure.calendar.database.RappelEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import javax.inject.Inject

class LocalDBRappelRepository @Inject constructor(private val rappelDao: RappelDao) :
    RappelRepository {
    override suspend fun scheduleRappel(rappel: Rappel) {
        rappelDao.scheduleRappel(
            RappelEntity(
                rappel.id,
                rappel.rappelDate.atZone(ZoneId.of("Europe/Paris")).toEpochSecond(),
                rappel.sujet
            )
        )
    }

    override suspend fun endRappel(rappelId: UUID) {
        rappelDao.closeRappel(
            rappelId,
            LocalDateTime.now().atZone(ZoneId.of("Europe/Paris")).toEpochSecond()
        )
    }

    override suspend fun findRappels(): List<Rappel> = withContext(Dispatchers.IO) {
        rappelDao.getOnGoingRappels().map { rappelEntity ->
            val rappelDate = LocalDateTime.ofInstant(
                Instant.ofEpochSecond(rappelEntity.rappelDate),
                ZoneId.of("Europe/Paris")
            )
            Rappel(
                rappelEntity.id,
                rappelDate,
                rappelEntity.sujet
            )
        }
    }
}