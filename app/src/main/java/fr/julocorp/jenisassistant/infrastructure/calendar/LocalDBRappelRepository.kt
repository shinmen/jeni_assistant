package fr.julocorp.jenisassistant.infrastructure.calendar

import fr.julocorp.jenisassistant.domain.common.Rappel
import fr.julocorp.jenisassistant.domain.common.repository.RappelRepository
import fr.julocorp.jenisassistant.infrastructure.common.database.RappelDao
import fr.julocorp.jenisassistant.infrastructure.common.database.RappelEntity
import java.util.*
import javax.inject.Inject

class LocalDBRappelRepository @Inject constructor(private val rappelDao: RappelDao) :
    RappelRepository {
    override suspend fun scheduleRappel(rappel: Rappel) {
        rappelDao.scheduleRappel(
            RappelEntity(
                rappel.id.toString(),
                rappel.rappelDate.timeInMillis,
                rappel.sujet
            )
        )
    }

    override suspend fun endRappel(rappelId: UUID) {

    }
}