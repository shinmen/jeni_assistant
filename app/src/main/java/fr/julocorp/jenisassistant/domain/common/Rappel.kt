package fr.julocorp.jenisassistant.domain.common

import java.time.LocalDateTime
import java.util.*

data class Rappel(
    val id: UUID,
    val rappelDate: LocalDateTime,
    val sujet: String,
    val closedAt: LocalDateTime? = null
)