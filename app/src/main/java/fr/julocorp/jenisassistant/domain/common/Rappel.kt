package fr.julocorp.jenisassistant.domain.common

import java.util.*

data class Rappel(
    val id: UUID,
    val rappelDate: Calendar,
    val sujet: String,
    val closedAt: Calendar? = null
)