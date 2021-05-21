package fr.julocorp.jenisassistant.domain.common.repository

import fr.julocorp.jenisassistant.domain.common.Rappel
import java.util.*

interface RappelRepository {
    fun scheduleRappel(rappel: Rappel)

    fun endRappel(rappelId: UUID)
}