package fr.julocorp.jenisassistant.domain.mandatVente

import java.util.*

data class Proprietaire(
    val id: UUID,
    val lastname: String,
    val firstname: String,
    val telephones: List<String>,
    val email: String?,
    val detail: String?
)