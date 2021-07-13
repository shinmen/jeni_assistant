package fr.julocorp.jenisassistant.domain.mandatVente

import java.util.*

data class Proprietaire(
    val id: UUID,
    val fullname: String,
    val telephones: List<String>,
    val email: String?,
    val comment: String?
)