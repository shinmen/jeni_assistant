package fr.julocorp.jenisassistant.domain.prospection

import java.util.*

class Prospection(
    val id: UUID,
    val prospectionDate: Date,
    val prospectionAction: ProspectionAction
)