package fr.julocorp.jenisassistant.domain.calendar.repository

import fr.julocorp.jenisassistant.domain.common.Rappel
import java.util.*

interface RappelRepository {
    suspend fun scheduleRappel(rappel: Rappel)

    suspend fun endRappel(rappelId: UUID)

    suspend fun findRappels(): List<Rappel>
}