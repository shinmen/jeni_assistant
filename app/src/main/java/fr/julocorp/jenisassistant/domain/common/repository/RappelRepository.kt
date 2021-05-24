package fr.julocorp.jenisassistant.domain.common.repository

import fr.julocorp.jenisassistant.domain.common.Rappel
import java.util.*

interface RappelRepository {
    suspend fun scheduleRappel(rappel: Rappel)

    suspend fun endRappel(rappelId: UUID)
}