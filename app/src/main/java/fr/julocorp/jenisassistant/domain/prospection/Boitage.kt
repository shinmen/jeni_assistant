package fr.julocorp.jenisassistant.domain.prospection

import java.util.*

data class Boitage(
    val id: UUID,
    val adresses: List<Pair<AdresseProspect, AdresseProspect>>
) : ProspectionAction