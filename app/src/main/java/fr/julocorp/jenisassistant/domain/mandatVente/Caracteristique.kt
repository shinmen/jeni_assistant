package fr.julocorp.jenisassistant.domain.mandatVente

import java.util.*

data class Caracteristique<T>(val id: UUID, val label: String, val valeur: T)
