package fr.julocorp.jenisassistant.infrastructure.common

import fr.julocorp.jenisassistant.domain.common.Rappel
import fr.julocorp.jenisassistant.domain.common.repository.RappelRepository
import java.util.*
import javax.inject.Inject

class LocalDBRappelRepository @Inject constructor() : RappelRepository {
    override fun scheduleRappel(rappel: Rappel) {
        rappel
    }

    override fun endRappel(rappelId: UUID) {

    }
}